package com.der3.sections.domain.model


sealed class PrayerTimesParams {

    data class ByCoordinates(
        val latitude: Double,
        val longitude: Double,
        val method: Int = 2,
        val school: Int = 0
    ) : PrayerTimesParams()

    data class ByDate(
        val date: String,
        val latitude: Double,
        val longitude: Double,
        val method: Int = 2,
        val school: Int = 0
    ) : PrayerTimesParams()

    data class ByCity(
        val city: String,
        val country: String,
        val state: String? = null,
        val method: Int = 2,
        val school: Int = 0
    ) : PrayerTimesParams()

    data class ByAddress(
        val address: String,
        val method: Int = 2,
        val school: Int = 0
    ) : PrayerTimesParams()
}

data class NextPrayerParams(
    val latitude: Double,
    val longitude: Double,
    val method: Int = 2
)

data class CalendarParams(
    val year: Int,
    val month: Int,
    val latitude: Double,
    val longitude: Double,
    val method: Int = 2,
    val isHijri: Boolean = false
)

data class YearlyCalendarParams(
    val year: Int,
    val latitude: Double,
    val longitude: Double,
    val method: Int = 2,
    val isHijri: Boolean = false
)

data class DateRangeParams(
    val startDate: String,
    val endDate: String,
    val latitude: Double,
    val longitude: Double,
    val method: Int = 2
)