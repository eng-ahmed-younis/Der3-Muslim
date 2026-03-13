package com.der3.sections.presentation.prayer_times.mvi

import com.der3.mvi.MviIntent

sealed interface PrayerTimeIntent : MviIntent {
    object LoadPrayerTimes : PrayerTimeIntent
    data class ToggleNotification(val prayerType: PrayerType) : PrayerTimeIntent
    data class ChangeCalculationMethod(val methodId: Int) : PrayerTimeIntent
    data object LoadMonthlyCalendar : PrayerTimeIntent
    data class ChangeSchool(val schoolId: Int) : PrayerTimeIntent
    data class ChangeTimeFormat(val is24Hour: Boolean) : PrayerTimeIntent
    object OpenQibla : PrayerTimeIntent
    object Back : PrayerTimeIntent
    object DismissError : PrayerTimeIntent
    data class ChangeLocation(val lat: Double, val lng: Double, val locationName: String) : PrayerTimeIntent
}
