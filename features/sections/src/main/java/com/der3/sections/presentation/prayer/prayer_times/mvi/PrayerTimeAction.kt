package com.der3.sections.presentation.prayer.prayer_times.mvi

import com.der3.mvi.MviAction
import com.der3.sections.domain.model.PrayerDetails
import com.der3.sections.domain.model.PrayerType
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto
import com.der3.shared.domain.model.prayer.CalculationMethod

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
  //  data class OnCalculationMethodsLoaded(val methods: List<CalculationMethod>) : PrayerTimeAction
    data class OnMethodChanged(val methodId: Int) : PrayerTimeAction
    data class OnSchoolChanged(val schoolId: Int) : PrayerTimeAction
    data class OnTimeFormatChanged(val is24Hour: Boolean) : PrayerTimeAction
    data class OnLocationChanged(val lat: Double, val lng: Double, val locationName: String) : PrayerTimeAction
    data class OnMonthlyCalendarLoaded(val calendar: List<PrayerTimesDto>) : PrayerTimeAction
}
