package com.der3.shared.data.mappers

import com.der3.shared.data.dto.prayer.*
import com.der3.shared.data.dto.prayer.base.MethodDetailDto
import com.der3.shared.data.dto.prayer.data.DateDto
import com.der3.shared.data.dto.prayer.data.GregorianDateDto
import com.der3.shared.data.dto.prayer.data.HijriDateDto
import com.der3.shared.data.dto.prayer.meta.MetaDto
import com.der3.shared.data.dto.prayer.meta.MethodDto
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto
import com.der3.shared.data.dto.prayer.timings.TimingsDto
import com.der3.shared.domain.model.prayer.*

fun PrayerTimesDto.toDomain(): PrayerTimes {
    return PrayerTimes(
        timings = timings.toDomain(),
        dateInfo = date.toDomain(),
        location = meta.toDomain()
    )
}

fun TimingsDto.toDomain(): Timings {
    return Timings(
        fajr = fajr.replace(" (UTC)", ""),
        sunrise = sunrise.replace(" (UTC)", ""),
        dhuhr = dhuhr.replace(" (UTC)", ""),
        asr = asr.replace(" (UTC)", ""),
        maghrib = maghrib.replace(" (UTC)", ""),
        isha = isha.replace(" (UTC)", ""),
        imsak = imsak.replace(" (UTC)", ""),
        midnight = midnight.replace(" (UTC)", ""),
        firstThird = firstThird?.replace(" (UTC)", ""),
        lastThird = lastThird?.replace(" (UTC)", "")
    )
}

fun DateDto.toDomain(): DateInfo {
    return DateInfo(
        readable = readable,
        gregorian = gregorian.toDomain(),
        hijri = hijri.toDomain()
    )
}

fun GregorianDateDto.toDomain(): GregorianDate {
    return GregorianDate(
        date = date,
        day = day,
        weekday = weekday.en,
        month = month.en,
        year = year
    )
}

fun HijriDateDto.toDomain(): HijriDate {
    return HijriDate(
        date = date,
        day = day,
        weekdayEn = weekday.en,
        weekdayAr = weekday.ar,
        monthEn = month.en,
        monthAr = month.ar,
        monthNumber = month.number,
        year = year,
        holidays = holidays
    )
}

fun MetaDto.toDomain(): LocationInfo {
    return LocationInfo(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        calculationMethod = method.toDomain()
    )
}

fun MethodDto.toDomain(): CalculationMethod {
    return CalculationMethod(
        id = id,
        name = name
    )
}

// For list mapping
fun List<PrayerTimesDto>.toDomain(): List<PrayerTimes> {
    return map { it.toDomain() }
}

// Next Prayer mapper
fun NextPrayerDto.toDomain(dateStr: String): NextPrayer {
    val prayerName = timings.keys.firstOrNull() ?: "Unknown"
    val prayerTime = timings.values.firstOrNull() ?: ""
    
    return NextPrayer(
        name = prayerName,
        time = prayerTime.replace(" (UTC)", ""),
        remainingTime = calculateRemainingTime(prayerTime),
        date = dateStr
    )
}

private fun calculateRemainingTime(prayerTime: String): String {
    // Implementation for calculating remaining time
    return "00:00"
}

// Methods mapper
fun Map<String, MethodDetailDto>.toDomainMethods(): List<PrayerMethod> {
    return map { (key, value) ->
        PrayerMethod(
            id = value.id,
            name = value.name,
            params = value.params
        )
    }.sortedBy { it.id }
}
