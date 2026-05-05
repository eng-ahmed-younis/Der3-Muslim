package com.der3.sections.presentation.qibla

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.model.AppStyle
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.qibla.components.CalibrationWarningCard
import com.der3.sections.presentation.qibla.components.CompassDial
import com.der3.sections.presentation.qibla.components.CompassNeedle
import com.der3.sections.presentation.qibla.components.LocationCard
import com.der3.sections.presentation.qibla.mvi.QiblaIntent
import com.der3.sections.presentation.qibla.mvi.QiblaState
import com.der3.sections.presentation.utils.qibla.hasLocationPermission
import com.der3.sections.presentation.utils.qibla.rememberCompassAzimuth
import com.der3.sections.presentation.utils.qibla.rememberLocationState
import com.der3.sections.presentation.utils.qibla.rememberQiblaDirection
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

/**
 * QiblaRoute serves as the entry point for the Qibla screen, managing ViewModel integration,
 * navigation effects, and error dialogs.
 */
@Composable
fun QiblaRoute(
    onNavigate: (Screens) -> Unit = {}
) {
    val viewModel = hiltViewModel<QiblaViewModel>()
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

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )


    QiblaScreen(
        state = viewModel.viewState,
        onIntent = viewModel::onIntent
    )
}

/**
 * QiblaScreen displays the compass UI, location information, and handles sensor updates.
 */
@Composable
fun QiblaScreen(
    state: QiblaState,
    onIntent: (QiblaIntent) -> Unit = {}
) {
    val context = LocalContext.current

    // State to track if location permission is granted
    var hasPermission by remember {
        mutableStateOf(context.hasLocationPermission())
    }

    // Launcher to request location permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.values.any { it }
    }

    // Request permissions on first launch if not already granted
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

    // Custom remember functions for location, compass azimuth, and Qibla direction calculation
    val locationState by rememberLocationState(hasLocationPermission = hasPermission)
    val azimuthState by rememberCompassAzimuth()
    val qiblaDirState by rememberQiblaDirection(locationState)

    // Notify ViewModel when location changes
    LaunchedEffect(locationState) {
        locationState?.let {
            onIntent(QiblaIntent.OnLocationChanged(it.latitude, it.longitude))
        }
    }

    // Notify ViewModel when direction or azimuth updates to calculate relative rotation
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
            .background(AppColors.screenBackground)
    ) {
        Der3TopAppBar(
            title = stringResource(id = com.der3.ui.R.string.qibla_title),
            backgroundColor = AppColors.screenBackground,
            showBackButton = true,
            onBackClick = { onIntent(QiblaIntent.OnBackClick) },
            trailingContent = {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (com.der3.ui.themes.isDarkTheme) AppColors.gold700 else AppColors.green800),
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Location Card
            LocationCard(state = state)

            Spacer(modifier = Modifier.height(48.dp))

            // Compass View
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.distanceToKaaba == 0.0 && state.currentLocationName == stringResource(id = com.der3.ui.R.string.locating)) {
                    androidx.compose.material3.CircularProgressIndicator(
                        color = AppColors.green800,
                        modifier = Modifier.size(48.dp)
                    )
                }

                // Background Circle
                val circleColor = if (com.der3.ui.themes.isDarkTheme) AppColors.gold700 else AppColors.green800
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawCircle(
                        color = circleColor.copy(alpha = 0.1f),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                // Force LTR for Compass to ensure East/West are correct
                androidx.compose.runtime.CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    val springSpec = spring<Float>(dampingRatio = 0.5f, stiffness = 2000f)

                    // Compass Dial — animated so it doesn't snap on fast turns
                    val animatedDialRotation by animateFloatAsState(
                        targetValue = -state.compassRotation,
                        animationSpec = springSpec,
                        label = "dial_rotation"
                    )
                    CompassDial(modifier = Modifier.rotate(animatedDialRotation))

                    // Qibla Needle
                    val animatedNeedleRotation by animateFloatAsState(
                        targetValue = state.qiblaDirection - state.compassRotation,
                        animationSpec = springSpec,
                        label = "needle_rotation"
                    )
                    CompassNeedle(modifier = Modifier.rotate(animatedNeedleRotation))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Guidance Text
            val guidanceColor by animateColorAsState(
                targetValue = if (state.isOnTarget) {
                    if (com.der3.ui.themes.isDarkTheme) AppColors.gold700 else AppColors.green800
                } else AppColors.gray500,
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
                color = if (com.der3.ui.themes.isDarkTheme) AppColors.gold700 else AppColors.green800
            )
            Text(
                text = state.directionText,
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.gray500,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calibration Warning
            CalibrationWarningCard(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun QiblaScreenPreviewLight() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
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

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun QiblaScreenPreviewDark() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
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
