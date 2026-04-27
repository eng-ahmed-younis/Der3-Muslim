package com.der3.sections.presentation.prayer.prayer_setting

import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer.prayer_setting.mvi.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerSettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    reducer: PrayerSettingReducer
) : MviBaseViewModel<PrayerSettingState, PrayerSettingAction, PrayerSettingIntent>(
    initialState = PrayerSettingState(),
    reducer = reducer
) {

    init {
        handleIntent(PrayerSettingIntent.LoadSettings)
    }

    override fun handleIntent(intent: PrayerSettingIntent) {
        when (intent) {
            is PrayerSettingIntent.LoadSettings -> loadSettings()
            is PrayerSettingIntent.ChangeLocation -> {
                onEffect(MviEffect.Navigate(Der3NavigationRoute.LocationPickerScreen))
            }
            is PrayerSettingIntent.SelectCalculationMethod -> {
                onAction(PrayerSettingAction.UpdateMethod(intent.methodId))
            }
            is PrayerSettingIntent.SelectMadhab -> {
                onAction(PrayerSettingAction.UpdateMadhab(intent.madhab))
            }
            is PrayerSettingIntent.SelectHighLatitudeMethod -> {
                onAction(PrayerSettingAction.UpdateHighLatitudeMethod(intent.method))
            }
            is PrayerSettingIntent.UpdateManualAdjustment -> {
                onAction(PrayerSettingAction.UpdateOffset(intent.prayer, intent.offset))
            }
            is PrayerSettingIntent.SaveSettings -> saveSettings()
            is PrayerSettingIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
            is PrayerSettingIntent.Retry -> loadSettings()
        }
    }

    private fun loadSettings() {
        val locationName = dataStoreRepository.locationName
        
        // Mocking manual offsets for now as they might not be in DataStore yet
        val offsets = PrayerType.entries.associateWith { 0 }

        onAction(
            PrayerSettingAction.SettingsLoaded(
                locationName = locationName ?: "",
                selectedMethodId = dataStoreRepository.prayerCalculationMethod ?: 2,
                selectedMadhab = if (dataStoreRepository.prayerSchool == 0) Madhab.SHAFI else Madhab.HANAFI,
                highLatitudeAdjustment = "Angle Based",
                manualOffsets = offsets
            )
        )
    }

    private fun saveSettings() {
        viewModelScope.launch {
            onAction(PrayerSettingAction.Saving(true))
            try {
                dataStoreRepository.prayerCalculationMethod = viewState.selectedMethodId
                dataStoreRepository.prayerSchool = viewState.selectedMadhab.value
                // Save offsets...
                
                onAction(PrayerSettingAction.Saving(false))
                onEffect(MviEffect.Navigate(Screens.Back()))
            } catch (e: Exception) {
                val error = e.message?.let { UiText.DynamicError(it) }
                    ?: UiText.ResourceError(com.der3.ui.R.string.error_save_settings)
                onAction(PrayerSettingAction.Error(e.message ?: "error_save_settings"))
                onEffect(MviEffect.OnErrorDialog(error))
            }
        }
    }
}
