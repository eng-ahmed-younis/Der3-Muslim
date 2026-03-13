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
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import java.util.TimeZone

fun PrayerTimesDto.toDomain(): PrayerTimes {
    return PrayerTimes(
        timings = timings.toDomain(),
        dateInfo = date.toDomain(),
        location = meta.toDomain()
    )
}

fun TimingsDto.toDomain(): Timings {
    val cleanTime = { time: String -> time.replace(Regex("\\s*\\(.*\\)"), "").trim() }
    return Timings(
        fajr = cleanTime(fajr),
        sunrise = cleanTime(sunrise),
        dhuhr = cleanTime(dhuhr),
        asr = cleanTime(asr),
        maghrib = cleanTime(maghrib),
        isha = cleanTime(isha),
        imsak = cleanTime(imsak),
        midnight = cleanTime(midnight),
        firstThird = firstThird?.let { cleanTime(it) },
        lastThird = lastThird?.let { cleanTime(it) }
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
fun NextPrayerDto.toDomain(dateStr: String, timezone: String? = null): NextPrayer {
    val prayerName = timings.keys.firstOrNull() ?: "Unknown"
    val prayerTime = timings.values.firstOrNull()?.replace(Regex("\\s*\\(.*\\)"), "")?.trim() ?: ""
    
    return NextPrayer(
        name = prayerName,
        time = prayerTime,
        remainingTime = calculateRemainingTime(prayerTime, timezone),
        date = dateStr
    )
}

private fun calculateRemainingTime(prayerTime: String, timezone: String?): String {
    try {
        val tz = timezone?.let { TimeZone.getTimeZone(it) } ?: TimeZone.getDefault()
        val currentTime = java.util.Calendar.getInstance(tz)
        val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.ENGLISH)
        format.timeZone = tz
        val prayerDate = format.parse(prayerTime.split(" ")[0]) ?: return "00:00:00"
        
        val prayerCalendar = java.util.Calendar.getInstance(tz).apply {
            time = prayerDate
            set(java.util.Calendar.YEAR, currentTime.get(java.util.Calendar.YEAR))
            set(java.util.Calendar.MONTH, currentTime.get(java.util.Calendar.MONTH))
            set(java.util.Calendar.DAY_OF_MONTH, currentTime.get(java.util.Calendar.DAY_OF_MONTH))
        }

        if (prayerCalendar.before(currentTime)) {
            prayerCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
        }

        val diff = prayerCalendar.timeInMillis - currentTime.timeInMillis
        val hours = (diff / (1000 * 60 * 60)).toInt()
        val minutes = ((diff / (1000 * 60)) % 60).toInt()
        val seconds = ((diff / 1000) % 60).toInt()

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } catch (e: Exception) {
        return "00:00:00"
    }
}

// Methods mapper
fun Map<String, MethodDetailDto>.toDomainMethods(): List<PrayerMethod> {
    return map { (_, value) ->
        PrayerMethod(
            id = value.id,
            name = value.name,
            params = value.params.mapValues { entry ->
                entry.value.jsonPrimitive.contentOrNull ?: entry.value.toString()
            }
        )
    }.sortedBy { it.id }
}
