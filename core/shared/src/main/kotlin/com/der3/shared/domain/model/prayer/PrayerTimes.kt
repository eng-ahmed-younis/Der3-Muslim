package com.der3.shared.domain.model.prayer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrayerTimes(
    val timings: Timings,
    val dateInfo: DateInfo,
    val location: LocationInfo
) : Parcelable

@Parcelize
data class Timings(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val imsak: String? = null,
    val midnight: String? = null,
    val firstThird: String? = null,
    val lastThird: String? = null
) : Parcelable

@Parcelize
data class DateInfo(
    val readable: String,
    val gregorian: GregorianDate,
    val hijri: HijriDate
) : Parcelable

@Parcelize
data class GregorianDate(
    val date: String,
    val day: String,
    val weekday: String,
    val month: String,
    val year: String
) : Parcelable

@Parcelize
data class HijriDate(
    val date: String,
    val day: String,
    val weekdayEn: String,
    val weekdayAr: String,
    val monthEn: String,
    val monthAr: String,
    val monthNumber: Int,
    val year: String,
    val holidays: List<String>? = null
) : Parcelable

@Parcelize
data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val calculationMethod: CalculationMethod
) : Parcelable

@Parcelize
data class CalculationMethod(
    val id: Int,
    val name: String
) : Parcelable

// Next Prayer Model
data class NextPrayer(
    val name: String,
    val time: String,
    val remainingTime: String,
    val date: String
)

// Prayer Method Model
data class PrayerMethod(
    val id: Int,
    val name: String,
    val params: Map<String, String> = emptyMap(),
    val isSelected: Boolean = false
)
