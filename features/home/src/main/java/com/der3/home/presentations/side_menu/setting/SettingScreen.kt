package com.der3.home.presentations.side_menu.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.der3.home.presentations.side_menu.setting.components.SettingActionItem
import com.der3.home.presentations.side_menu.setting.components.SettingCard
import com.der3.home.presentations.side_menu.setting.components.SettingClickableItem
import com.der3.home.presentations.side_menu.setting.components.SettingSection
import com.der3.home.presentations.side_menu.setting.components.SettingSliderItem
import com.der3.home.presentations.side_menu.setting.components.SettingToggleItem
import com.der3.home.presentations.side_menu.setting.mvi.SettingIntent
import com.der3.home.presentations.side_menu.setting.mvi.SettingState
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.Der3TopAppBar
import com.der3.ui.components.ErrorDialog
import com.der3.ui.components.LoadingDialog
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isStatusBarDark
import com.der3.utils.asString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun SettingRoute(
    onNavigate: (Screens) -> Unit = {},
) {
    val viewModel = hiltViewModel<SettingViewModel>()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val state = viewModel.viewState
    val onIntent = viewModel::onIntent

    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)
                is MviEffect.OnErrorDialog -> {
                    errorMessage = it.error.asString(context)
                    showErrorDialog = true
                }
                else -> {}
            }
        }.launchIn(this)
    }

    LoadingDialog(visible = viewModel.viewState.isLoading)

    ErrorDialog(
        visible = showErrorDialog,
        message = errorMessage,
        onRetry = {
            showErrorDialog = false
            viewModel.onIntent(SettingIntent.Retry)
        },
        onDismiss = {
            showErrorDialog = false
            viewModel.onIntent(SettingIntent.DismissError)
        }
    )

    ShiftSystemBarStyle(
        statusBarColor = AppColors.screenBackground,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = isStatusBarDark,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false
    )

    SettingScreen(
        state = state,
        onIntent = onIntent
    )
}

@Composable
fun SettingScreen(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.screenBackground)
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.settings_title),
            backgroundColor = Color.Transparent,
            onBackClick = {
                onIntent(SettingIntent.Back)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Display Settings
            SettingSection(title = stringResource(id = R.string.settings_display)) {
                SettingCard {
                    SettingToggleItem(
                        title = stringResource(id = R.string.settings_night_mode),
                        description = stringResource(id = R.string.settings_night_mode_desc),
                        icon = Icons.Default.DarkMode,
                        checked = state.isNightMode,
                        onCheckedChange = { onIntent(SettingIntent.OnNightModeChange(isNightMode = it)) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                    SettingSliderItem(
                        title = stringResource(id = R.string.settings_font_size),
                        value = state.fontSize.toFloat(),
                        onValueChange = { onIntent(SettingIntent.OnFontSizeChange(it.toInt())) },
                        icon = Icons.Default.TextFields
                    )
                }
            }

            // Audio Settings
            SettingSection(title = stringResource(id = R.string.settings_audio)) {
                SettingCard {
                    SettingToggleItem(
                        title = stringResource(id = R.string.settings_auto_play),
                        description = stringResource(id = R.string.settings_auto_play_desc),
                        icon = Icons.Default.PlayCircleFilled,
                        checked = state.isAutoPlayEnabled,
                        onCheckedChange = { onIntent(SettingIntent.OnAutoPlayChange(it)) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                    SettingClickableItem(
                        title = stringResource(id = R.string.settings_playback_speed),
                        value = "${state.playbackSpeed}x",
                        icon = Icons.Default.SettingsSuggest,
                        onClick = { /* speed selector logic */ }
                    )
                }
            }

            // Reminders
            SettingSection(title = stringResource(id = R.string.settings_reminders)) {
                SettingCard {
                    SettingToggleItem(
                        title = stringResource(id = R.string.settings_morning_azkar),
                        description = stringResource(id = R.string.settings_morning_azkar_desc, state.morningReminderTime),
                        icon = Icons.Default.DarkMode, // Replace with appropriate icon
                        checked = state.isMorningRemindersEnabled,
                        onCheckedChange = { onIntent(SettingIntent.OnMorningRemindersChange(it)) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                    SettingToggleItem(
                        title = stringResource(id = R.string.settings_evening_azkar),
                        description = stringResource(id = R.string.settings_evening_azkar_desc, state.eveningReminderTime),
                        icon = Icons.Default.DarkMode,
                        checked = state.isEveningRemindersEnabled,
                        onCheckedChange = { onIntent(SettingIntent.OnEveningRemindersChange(it)) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                    SettingToggleItem(
                        title = stringResource(id = R.string.settings_sleeping_azkar),
                        description = stringResource(id = R.string.settings_sleeping_azkar_desc, state.sleepingReminderTime),
                        icon = Icons.Default.DarkMode,
                        checked = state.isSleepingRemindersEnabled,
                        onCheckedChange = { onIntent(SettingIntent.OnSleepingRemindersChange(it)) }
                    )
                }
            }

            // Footer actions
            SettingCard {
                SettingActionItem(
                    title = stringResource(id = R.string.settings_share_app),
                    icon = Icons.Default.Share,
                    onClick = { onIntent(SettingIntent.ShareApp) }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                SettingActionItem(
                    title = stringResource(id = R.string.settings_rate_app),
                    icon = Icons.Default.Star,
                    onClick = { onIntent(SettingIntent.RateApp) }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = AppColors.gray50)
                SettingActionItem(
                    title = stringResource(id = R.string.settings_about_app),
                    icon = Icons.Default.Info,
                    onClick = { onIntent(SettingIntent.AboutApp) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreenPreviewAr() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        SettingScreen(
            state = SettingState(),
            onIntent = {}
        )
    }
}
