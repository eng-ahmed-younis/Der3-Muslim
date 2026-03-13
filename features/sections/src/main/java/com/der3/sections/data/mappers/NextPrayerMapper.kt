package com.der3.sections.data.mappers

import com.der3.sections.domain.model.NextPrayerInfo
import com.der3.sections.domain.model.NextPrayerInfoBuilder
import com.der3.shared.data.dto.prayer.NextPrayerDto


fun NextPrayerDto.toNextPrayerInfo(
    latitude: Double? = null,
    longitude: Double? = null,
    methodId: Int? = null
): NextPrayerInfo {
    val prayerName = timings.keys.firstOrNull() ?: "Unknown"
    val prayerTime = timings.values.firstOrNull()?.replace(Regex("\\s*\\(.*\\)"), "")?.trim() ?: ""

    return NextPrayerInfoBuilder()
        .setPrayerName(prayerName)
        .setPrayerTime(prayerTime)
        .setGregorianDate(date.readable)
        .setHijriDate("${date.hijri.day} ${date.hijri.month.en} ${date.hijri.year}")
        .setCoordinates(latitude ?: 0.0, longitude ?: 0.0)
        .setMethodId(methodId ?: 0)
        .build()
}

fun List<Pair<String, String>>.toNextPrayerInfoList(
    gregorianDate: String,
    hijriDate: String
): List<NextPrayerInfo> {
    return map { (name, time) ->
        NextPrayerInfoBuilder()
            .setPrayerName(name)
            .setPrayerTime(time.replace(Regex("\\s*\\(.*\\)"), "").trim())
            .setGregorianDate(gregorianDate)
            .setHijriDate(hijriDate)
            .build()
    }
}