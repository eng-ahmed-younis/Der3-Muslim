package com.der3.sections.presentation.prayer_times.mvi

import com.der3.mvi.MviAction

sealed interface PrayerTimeAction : MviAction {
    data class OnPrayerTimesLoaded(
        val location: String,
        val hijriDate: String,
        val gregorianDate: String,
        val prayerTimes: List<PrayerDetails>
    ) : PrayerTimeAction
    data class OnNotificationToggled(val prayerType: PrayerType) : PrayerTimeAction
    data class OnLoading(val isLoading: Boolean) : PrayerTimeAction
    data class OnError(val message: String?) : PrayerTimeAction
    data object ClearError : PrayerTimeAction
}
