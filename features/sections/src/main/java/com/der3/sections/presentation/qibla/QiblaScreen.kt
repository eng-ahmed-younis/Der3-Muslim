package com.der3.sections.presentation.qibla

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.der3.sections.presentation.utils.qibla.rememberCompassAzimuth
import com.der3.sections.presentation.utils.qibla.rememberLocationState
import com.der3.sections.presentation.utils.qibla.rememberQiblaDirection
import com.der3.sections.presentation.utils.qibla.hasLocationPermission
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.qibla.mvi.QiblaIntent
import com.der3.sections.presentation.qibla.mvi.QiblaState
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun QiblaRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel = hiltViewModel<QiblaViewModel>()
    val state = viewModel.viewState
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    showErrorDialog = true
                }
            }
        }.launchIn(scope)
    }

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            viewModel.onIntent(QiblaIntent.Retry)
            showErrorDialog = false
            errorMessage = null
        },
        onDismiss = {
            showErrorDialog = false
            errorMessage = null
        }
    )


    QiblaScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun QiblaScreen(
    state: QiblaState,
    onIntent: (QiblaIntent) -> Unit = {}
) {
    val context = LocalContext.current

    var hasPermission by remember { 
        mutableStateOf(context.hasLocationPermission()) 
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.values.any { it }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val locationState by rememberLocationState(hasLocationPermission = hasPermission)
    val azimuthState by rememberCompassAzimuth(sensorDelay = android.hardware.SensorManager.SENSOR_DELAY_FASTEST)
    val qiblaDirState by rememberQiblaDirection(locationState)

    LaunchedEffect(locationState) {
        locationState?.let {
            onIntent(QiblaIntent.OnLocationChanged(it.latitude, it.longitude))
        }
    }

    LaunchedEffect(qiblaDirState, azimuthState) {
        val qDir = qiblaDirState
        val azm = azimuthState
        if (qDir != null && azm != null) {
            onIntent(QiblaIntent.OnDirectionUpdated(qDir, azm))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {
        Der3TopAppBar(
            title = "اتجاه القبلة",
            backgroundColor = AppColors.gray50,
            showBackButton = true,
            onBackClick = { onIntent(QiblaIntent.OnBackClick) },
            trailingContent = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AppColors.green800),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scrollable(
                    state = rememberScrollState(),
                    orientation = Orientation.Vertical
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Location Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = AppColors.green700,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = "موقعك الحالي",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColors.green700,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.currentLocationName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "المسافة إلى الكعبة: ${String.format(Locale("ar"), "%.1f", state.distanceToKaaba)} كم",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.gray500
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Compass View
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.distanceToKaaba == 0.0 && state.currentLocationName == "جاري تحديد الموقع...") {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = AppColors.green800,
                        modifier = Modifier.size(48.dp)
                    )
                }

                // Background Circle
                val green800 = AppColors.green800
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawCircle(
                        color = green800.copy(alpha = 0.1f),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                // Force LTR for Compass to ensure East/West are correct
                androidx.compose.runtime.CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    // Compass Dial
                    CompassDial(modifier = Modifier.rotate(-state.compassRotation))

                    // Qibla Needle
                    val animatedRotation by animateFloatAsState(
                        targetValue = state.qiblaDirection - state.compassRotation,
                        animationSpec = spring(
                            dampingRatio = 0.5f,
                            stiffness = 2000f
                        ),
                        label = "needle_rotation"
                    )

                    CompassNeedle(modifier = Modifier.rotate(animatedRotation))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Guidance Text
            val guidanceColor by animateColorAsState(
                targetValue = if (state.isOnTarget) AppColors.green800 else AppColors.gray500,
                label = "guidance_color"
            )
            
            Text(
                text = state.guidanceText,
                style = MaterialTheme.typography.titleLarge,
                color = guidanceColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Degrees Text
            Text(
                text = "${state.qiblaDirection.toInt()}°",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.green800
            )
            Text(
                text = state.directionText,
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.gray500,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            // Calibration Warning
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF6E3)), // Light yellowish
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF9EBC8))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "يرجى تحريك الهاتف بشكل 8 لمعايرة البوصلة وضمان دقة الاتجاه.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFB8942E),
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFFD4A017),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CompassDial(modifier: Modifier = Modifier) {
    val gray200 = AppColors.gray200
    val gray500 = AppColors.gray500
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Outer faint circle

            drawCircle(
                color = gray200.copy(alpha = 0.5f),
                style = Stroke(width = 1.dp.toPx())
            )
            // Dotted circle
            drawCircle(
                color = gray200,
                radius = size.width / 2 * 0.85f,
                style = Stroke(
                    width = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            )
        }
        Text(
            text = "ش",
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp),
            color = gray500,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "ج",
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp),
            color = gray500,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "ق",
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 10.dp),
            color = gray500,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = "غ",
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 10.dp),
            color = gray500,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CompassNeedle(modifier: Modifier = Modifier) {
    val green800 = AppColors.green800
    val gold = Color(0xFFD4A017)
    
    Canvas(modifier = modifier.size(240.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        
        // Needle Shaft
        // Green Tail
        drawLine(
            color = green800,
            start = center,
            end = Offset(centerX, centerY + 80.dp.toPx()),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
        
        // Gold Shaft
        drawLine(
            color = gold,
            start = center,
            end = Offset(centerX, centerY - 80.dp.toPx()),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )
        
        // Arrow Head
        val headPath = Path().apply {
            moveTo(centerX, centerY - 105.dp.toPx()) // Tip
            lineTo(centerX - 12.dp.toPx(), centerY - 80.dp.toPx())
            lineTo(centerX + 12.dp.toPx(), centerY - 80.dp.toPx())
            close()
        }
        drawPath(headPath, color = gold)
        
        // Center Hub
        drawCircle(
            color = green800,
            radius = 10.dp.toPx(),
            center = center
        )
        drawCircle(
            color = gold,
            radius = 10.dp.toPx(),
            center = center,
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QiblaScreenPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        QiblaScreen(state = QiblaState(
            qiblaDirection = 295f,
            directionText = "شمال غرب",
            currentLocationName = "مكة المكرمة، المملكة العربية السعودية",
            distanceToKaaba = 0.5
        ))
    }
}
