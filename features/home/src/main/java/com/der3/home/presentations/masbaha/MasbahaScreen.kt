package com.der3.home.presentations.masbaha

import android.content.res.Configuration
import androidx.compose.foundation.background
import com.der3.model.AppStyle
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.der3.home.di.factory.MasbahaViewModelFactory
import com.der3.home.presentations.masbaha.components.AzkarAutoSelected
import com.der3.home.presentations.masbaha.components.MasbahaActionButton
import com.der3.home.presentations.masbaha.components.ResetMasbahaDialog
import com.der3.home.presentations.masbaha.components.TargetDialog
import com.der3.home.presentations.masbaha.mvi.MasbahaIntent
import com.der3.home.presentations.masbaha.mvi.MasbahaState
import com.der3.home.presentations.zekr_details.ZekrDetailsViewModel
import com.der3.home.utils.performTasbeehHaptic
import com.der3.model.TasbeehHapticType
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.shared.params.MasbahaParams
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.InternetRequiredDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun MasbahaRoute(
    params: MasbahaParams,
    onNavigate: (Screens) -> Unit = {}
) {

    val viewModel: MasbahaViewModel =
        hiltViewModel<MasbahaViewModel, MasbahaViewModelFactory> { factory ->
            factory.create(params)
        }

    val state = viewModel.viewState
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current



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
            showErrorDialog = false
            viewModel.onIntent(MasbahaIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(MasbahaIntent.DismissError)
        }
    )

    InternetRequiredDialog(
        visibility = state.showInternetRequiredDialog,
        message = stringResource(id = R.string.internet_required_message),
        onActivateClick = {
            viewModel.onIntent(MasbahaIntent.OpenNetworkSettings)
            viewModel.onIntent(MasbahaIntent.ShowInternetRequiredDialog(show = false))
        },
        onTryLaterClick = {
            viewModel.onIntent(MasbahaIntent.ShowInternetRequiredDialog(show = false))
        },
        onDismiss = {
            viewModel.onIntent(MasbahaIntent.ShowInternetRequiredDialog(show = false))
        }
    )


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onIntent(MasbahaIntent.RefreshAfterBack)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )

    MasbahaScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasbahaScreen(
    state: MasbahaState,
    onIntent: (MasbahaIntent) -> Unit
) {
    var showTargetDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    if (showTargetDialog) {
        TargetDialog(
            currentTarget = state.targetCount,
            onDismiss = { showTargetDialog = false },
            onConfirm = {
                onIntent(MasbahaIntent.SetTargetCount(it))
                showTargetDialog = false
            }
        )
    }

    if (showResetDialog) {
        ResetMasbahaDialog(
            onDismiss = { showResetDialog = false },
            onConfirm = {
                onIntent(MasbahaIntent.ResetCount)
                showResetDialog = false
            }
        )
    }

    LoadingDialog(visible = state.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColors.screenBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showMenu by remember { mutableStateOf(false) }

        Der3TopAppBar(
            title = stringResource(id = R.string.electronic_rosary_title),
            backgroundColor = AppColors.screenBackground,
            titleColor = AppColors.gray900Text,
            showBackButton = if (state.showBackButton) true else false,
            onBackClick = {
                onIntent(MasbahaIntent.OnBackClick)
            },
            navigationIconColor = AppColors.green800,
            trailingContent = {
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = AppColors.gray900Text
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .background(
                                color = AppColors.cardColor
                            ),
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.history_title),
                                    color = AppColors.gray900Text,
                                )
                                   },
                            onClick = {
                                showMenu = false
                                onIntent(MasbahaIntent.OpenHistory)
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = AppColors.gray900Text
                                )
                            }
                        )
                    }
                }

            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Auto Switch and Label
            AzkarAutoSelected(
                autoSwitch = state.autoSwitch,
                onAutoSwitchChange = { onIntent(MasbahaIntent.ToggleAutoSwitch(it)) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Azkar Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(state.azkars) { azkar ->
                    val isSelected = state.selectedAzkar?.id == azkar.id
                    val chipBackgroundColor = when {
                        isSelected && isDarkTheme -> AppColors.gold700
                        isSelected -> AppColors.green800
                        else -> AppColors.cardColor
                    }
                    val chipContentColor = when {
                        isSelected && isDarkTheme -> AppColors.green900
                        isSelected -> AppColors.white
                        else -> if (isDarkTheme) AppColors.gray500 else AppColors.green700
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(chipBackgroundColor)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) chipBackgroundColor else if (isDarkTheme) AppColors.gray100 else AppColors.green50,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable { onIntent(MasbahaIntent.SelectAzkar(azkar)) }
                            .padding(horizontal = 24.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            text = azkar.text ?: "",
                            color = chipContentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val targetText = if (state.targetCount == -1) {
                    stringResource(id = R.string.target_unlimited)
                } else {
                    String.format(Locale("ar"), "%d", state.targetCount)
                }
                Text(
                    text = "${stringResource(id = R.string.target_label)}: $targetText",
                    color = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                    style = MaterialTheme.typography.bodyMedium
                )

                val progressText = if (state.targetCount == -1) {
                    "-"
                } else {
                    String.format(Locale("ar"), "%d%%", state.progressPercentage)
                }
                Text(
                    text = "${stringResource(id = R.string.progress_label)}: $progressText",
                    color = AppColors.gold600,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { if (state.targetCount == -1) 0f else state.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                trackColor = if (isDarkTheme) AppColors.green700.copy(alpha = 0.1f) else AppColors.gray200,
            )

            Spacer(modifier = Modifier.height(48.dp))

            val tasbeehMainCenterColor = if (isDarkTheme) AppColors.gold700 else AppColors.green800
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(color = tasbeehMainCenterColor.copy(alpha = 0.05f), shape = CircleShape)
                    .padding(20.dp)
                    .background(color = tasbeehMainCenterColor.copy(alpha = 0.1f), shape = CircleShape)
                    .padding(20.dp)
                    .clip(CircleShape)
                    .background(color = tasbeehMainCenterColor)
                    .clickable {
                        val hapticType = when (state.vibrationType) {
                            com.der3.home.presentations.masbaha.mvi.VibrationType.NONE -> TasbeehHapticType.NONE
                            com.der3.home.presentations.masbaha.mvi.VibrationType.SHORT -> TasbeehHapticType.SHORT
                            com.der3.home.presentations.masbaha.mvi.VibrationType.LONG -> TasbeehHapticType.LONG
                            com.der3.home.presentations.masbaha.mvi.VibrationType.HEARTBEAT -> TasbeehHapticType.HEART_BEAT
                        }
                        performTasbeehHaptic(haptic, hapticType)
                        onIntent(MasbahaIntent.IncrementCount)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format(Locale("ar"), "%d", state.currentCount),
                        color = if (isDarkTheme) AppColors.green900 else AppColors.white,
                        fontSize = 70.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.press_to_praise),
                        color = (if (isDarkTheme) AppColors.green900 else AppColors.white).copy(alpha = 0.8f),
                        fontWeight = FontWeight.W800,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MasbahaActionButton(
                    text = stringResource(id = R.string.reset),
                    icon = Icons.Default.Refresh,
                    modifier = Modifier.weight(1f),
                    onClick = { showResetDialog = true }
                )
                MasbahaActionButton(
                    text = stringResource(id = R.string.edit_target),
                    icon = Icons.Default.Settings,
                    modifier = Modifier.weight(1f),
                    onClick = { showTargetDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Sound Switch
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(28.dp),
                color = AppColors.cardColor,
                border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.gray100)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Switch(
                        modifier = Modifier.scale(0.8f),
                        checked = state.isSoundEnabled,
                        onCheckedChange = { onIntent(MasbahaIntent.ToggleSound(it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = if (isDarkTheme) AppColors.gray200 else AppColors.gray200,
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.sound_alert),
                            color = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = null,
                            tint = if (isDarkTheme) AppColors.gold700 else AppColors.green800
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Vibration Selector
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                color = AppColors.cardColor,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else AppColors.gray100
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.vibration),
                            color = if (isDarkTheme) AppColors.gold700 else AppColors.green800,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = null,
                            tint = if (isDarkTheme) AppColors.gold700 else AppColors.green800
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else AppColors.gray100)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        VibrationTypeItem(
                            text = "قصير",
                            icon = Icons.AutoMirrored.Filled.Notes,
                            isSelected = state.vibrationType == com.der3.home.presentations.masbaha.mvi.VibrationType.SHORT,
                            modifier = Modifier.weight(1f),
                            onClick = { onIntent(MasbahaIntent.ToggleVibration(com.der3.home.presentations.masbaha.mvi.VibrationType.SHORT)) }
                        )
                        VibrationTypeItem(
                            text = "طويل",
                            icon = Icons.AutoMirrored.Filled.Notes,
                            isSelected = state.vibrationType == com.der3.home.presentations.masbaha.mvi.VibrationType.LONG,
                            modifier = Modifier.weight(1f),
                            onClick = { onIntent(MasbahaIntent.ToggleVibration(com.der3.home.presentations.masbaha.mvi.VibrationType.LONG)) }
                        )
                        VibrationTypeItem(
                            text = "نبض القلب",
                            icon = Icons.Default.Favorite,
                            isSelected = state.vibrationType == com.der3.home.presentations.masbaha.mvi.VibrationType.HEARTBEAT,
                            modifier = Modifier.weight(1f),
                            onClick = { onIntent(MasbahaIntent.ToggleVibration(com.der3.home.presentations.masbaha.mvi.VibrationType.HEARTBEAT)) }
                        )
                        VibrationTypeItem(
                            text = "بدون",
                            icon = Icons.Default.StopCircle,
                            isSelected = state.vibrationType == com.der3.home.presentations.masbaha.mvi.VibrationType.NONE,
                            modifier = Modifier.weight(1f),
                            onClick = { onIntent(MasbahaIntent.ToggleVibration(com.der3.home.presentations.masbaha.mvi.VibrationType.NONE)) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun VibrationTypeItem(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected && isDarkTheme -> AppColors.gold700
        isSelected -> AppColors.green800
        else -> AppColors.cardColor
    }
    val contentColor = when {
        isSelected && isDarkTheme -> AppColors.green900
        isSelected -> AppColors.white
        else -> if (isDarkTheme) AppColors.gray500 else AppColors.gray500
    }

    Surface(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else AppColors.gray200
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected && isDarkTheme) AppColors.green900 else if (isSelected) AppColors.white else if (isDarkTheme) AppColors.gray500 else AppColors.green800,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = text,
                color = contentColor,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, name = "Vibration Item Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Vibration Item Dark")
@Composable
fun VibrationTypeItemPreview() {
    Der3MuslimTheme(
        style = if (androidx.compose.foundation.isSystemInDarkTheme()) AppStyle.DARK else AppStyle.LIGHT
    ) {
        Row(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VibrationTypeItem(
                text = "قصير",
                icon = Icons.AutoMirrored.Filled.Notes,
                isSelected = true,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
            VibrationTypeItem(
                text = "طويل",
                icon = Icons.AutoMirrored.Filled.Notes,
                isSelected = false,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true, name = "Masbaha Light")
@Composable
fun MasbahaScreenPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        MasbahaScreen(
            state = MasbahaState(
                azkars = listOf(
                    MasbahaAzkar(1, "سبحان الله", 33),
                    MasbahaAzkar(2, "الحمد لله", 33),
                    MasbahaAzkar(3, "الله أكبر", 33)
                ),
                currentCount = 21,
                targetCount = 33,
                selectedAzkar = MasbahaAzkar(id = 3, text = "سبحان الله")
            ),
            onIntent = {}
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Masbaha Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MasbahaScreenDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        MasbahaScreen(
            state = MasbahaState(
                azkars = listOf(
                    MasbahaAzkar(1, "سبحان الله", 33),
                    MasbahaAzkar(2, "الحمد لله", 33),
                    MasbahaAzkar(3, "الله أكبر", 33)
                ),
                currentCount = 21,
                targetCount = 33,
                selectedAzkar = MasbahaAzkar(id = 3, text = "سبحان الله")
            ),
            onIntent = {}
        )
    }
}
