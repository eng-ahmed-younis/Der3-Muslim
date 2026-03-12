package com.der3.sections.presentation.prayer_times

import androidx.lifecycle.viewModelScope
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer_times.mvi.PrayerDetails
import com.der3.sections.presentation.prayer_times.mvi.PrayerTimeAction
import com.der3.sections.presentation.prayer_times.mvi.PrayerTimeIntent
import com.der3.sections.presentation.prayer_times.mvi.PrayerTimeReducer
import com.der3.sections.presentation.prayer_times.mvi.PrayerTimeState
import com.der3.sections.presentation.prayer_times.mvi.PrayerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    reducer: PrayerTimeReducer
) : MviBaseViewModel<PrayerTimeState, PrayerTimeAction, PrayerTimeIntent>(
    initialState = PrayerTimeState(),
    reducer = reducer
) {

    init {
        handleIntent(PrayerTimeIntent.LoadPrayerTimes)
    }

    public override fun handleIntent(intent: PrayerTimeIntent) {
        when (intent) {
            is PrayerTimeIntent.LoadPrayerTimes -> loadPrayerTimes()
            is PrayerTimeIntent.ToggleNotification -> {
                onAction(PrayerTimeAction.OnNotificationToggled(intent.prayerType))
            }
            is PrayerTimeIntent.OpenQibla -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.QiblaScreen))
            }
            is PrayerTimeIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
            is PrayerTimeIntent.DismissError -> {
                onAction(PrayerTimeAction.ClearError)
            }
        }
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            onAction(PrayerTimeAction.OnLoading(true))
            
            // Mocking data for now based on the image provided
            val mockPrayerTimes = listOf(
                PrayerDetails("الفجر", "04:42 ص", isNext = false, isPassed = true, type = PrayerType.FAJR),
                PrayerDetails("الشروق", "06:01 ص", isNext = false, isPassed = true, type = PrayerType.SUNRISE),
                PrayerDetails("الظهر", "12:05 م", isNext = false, isPassed = true, type = PrayerType.DHUHR),
                PrayerDetails("العصر", "03:45 م", isNext = true, isPassed = false, type = PrayerType.ASR),
                PrayerDetails("المغرب", "06:12 م", isNext = false, isPassed = false, type = PrayerType.MAGHRIB),
                PrayerDetails("العشاء", "07:42 م", isNext = false, isPassed = false, type = PrayerType.ISHA)
            )

            onAction(
                PrayerTimeAction.OnPrayerTimesLoaded(
                    location = "الرياض، المملكة العربية السعودية",
                    hijriDate = "الأربعاء 15 رمضان 1445 هـ",
                    gregorianDate = "27 مارس 2024",
                    prayerTimes = mockPrayerTimes
                )
            )
        }
    }
}
