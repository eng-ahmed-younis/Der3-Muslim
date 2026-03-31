package com.der3.home.presentations.side_menu.setting

import com.der3.data_store.api.DataStoreRepository
import com.der3.home.presentations.side_menu.setting.mvi.SettingAction
import com.der3.home.presentations.side_menu.setting.mvi.SettingIntent
import com.der3.home.presentations.side_menu.setting.mvi.SettingReducer
import com.der3.home.presentations.side_menu.setting.mvi.SettingState
import com.der3.model.AppStyle
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : MviBaseViewModel<SettingState, SettingAction, SettingIntent>(
    initialState = SettingState(
        fontSize = dataStoreRepository.zekrScreenDetailsFontSize,
        playbackSpeed = dataStoreRepository.playbackSpeed
    ),
    reducer = SettingReducer()
) {

    init {
        loadUiStyle()
    }


    override fun handleIntent(intent: SettingIntent) {
        when (intent) {
            is SettingIntent.OnNightModeChange -> {
                updateNightMode(intent.isNightMode)
                onAction(SettingAction.UpdateNightMode(intent.isNightMode))
            }
            is SettingIntent.OnFontSizeChange -> {
                dataStoreRepository.zekrScreenDetailsFontSize = intent.fontSize
                onAction(SettingAction.UpdateFontSize(intent.fontSize))
            }
            is SettingIntent.OnAutoPlayChange -> {
                onAction(SettingAction.UpdateAutoPlay(intent.isAutoPlayEnabled))
            }
            is SettingIntent.OnPlaybackSpeedChange -> {
                dataStoreRepository.playbackSpeed = intent.speed
                onAction(SettingAction.UpdatePlaybackSpeed(intent.speed))
            }
            is SettingIntent.OnMorningRemindersChange -> {
                onAction(SettingAction.UpdateMorningReminders(intent.isEnabled))
            }
            is SettingIntent.OnEveningRemindersChange -> {
                onAction(SettingAction.UpdateEveningReminders(intent.isEnabled))
            }
            is SettingIntent.OnSleepingRemindersChange -> {
                onAction(SettingAction.UpdateSleepingReminders(intent.isEnabled))
            }
            SettingIntent.Back -> onEffect(MviEffect.Navigate(Screens.Back()))
            SettingIntent.DismissError -> onAction(SettingAction.UpdateError(null))
            SettingIntent.Retry -> {
                // Retry logic if needed
            }
            SettingIntent.AboutApp -> onEffect(MviEffect.Navigate(Der3NavigationRoute.AboutScreen))
            SettingIntent.RateApp -> onEffect(MviEffect.Navigate(Der3NavigationRoute.RateScreen))
            SettingIntent.ShareApp -> onEffect(MviEffect.Navigate(Der3NavigationRoute.ShareScreen))
        }
    }



    private fun updateNightMode(isNightMode: Boolean) {
        if (isNightMode) {
            dataStoreRepository.appStyle = AppStyle.DARK.value
        } else {
            dataStoreRepository.appStyle = AppStyle.LIGHT.value
        }
        updateUseDarkStatusBarIcons(isNightMode)
    }

    private fun loadUiStyle() {
        dataStoreRepository.appStyle.let {
            val nightMode = when (it) {
                AppStyle.LIGHT.value -> false
                AppStyle.DARK.value -> true
                else -> false
            }
            onAction(SettingAction.UpdateNightMode(isNightMode = nightMode))
            updateUseDarkStatusBarIcons(nightMode)
        }
    }

    private fun updateUseDarkStatusBarIcons(isNightMode: Boolean) {
        if (isNightMode) {
            onAction(SettingAction.UpdateUseDarkStatusBarIcons(false))
        } else {
            onAction(SettingAction.UpdateUseDarkStatusBarIcons(true))
        }
    }


}
