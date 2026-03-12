package com.der3.sections.presentation.prayer_times.mvi

import com.der3.mvi.MviIntent

sealed interface PrayerTimeIntent : MviIntent {
    object LoadPrayerTimes : PrayerTimeIntent
    data class ToggleNotification(val prayerType: PrayerType) : PrayerTimeIntent
    object OpenQibla : PrayerTimeIntent
    object Back : PrayerTimeIntent
    object DismissError : PrayerTimeIntent
}
