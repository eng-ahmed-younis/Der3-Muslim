package com.der3.shared.data.mappers

import com.der3.shared.data.dto.prayer.NextPrayerDto
import com.der3.shared.data.dto.prayer.data.DateDto
import com.der3.shared.data.dto.prayer.data.GregorianDateDto
import com.der3.shared.data.dto.prayer.data.HijriDateDto
import com.der3.shared.data.dto.prayer.timings.GregorianMonthDto
import com.der3.shared.data.dto.prayer.timings.HijriMonthDto
import com.der3.shared.data.dto.prayer.timings.TimingsDto
import com.der3.shared.data.dto.prayer.timings.WeekdayArDto
import com.der3.shared.data.dto.prayer.timings.WeekdayEnDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PrayerTimesMapperTest {

    @Test
    fun `TimingsDto toDomain cleans time strings with timezone offsets`() {
        val dto = TimingsDto(
            fajr = "04:30 (+03)",
            sunrise = "06:00 (+03)",
            dhuhr = "12:15 (+03)",
            asr = "15:30 (+03)",
            maghrib = "18:20 (+03)",
            isha = "19:50 (+03)",
            imsak = "04:20 (+03)",
            midnight = "00:00 (+03)"
        )

        val domain = dto.toDomain()

        assertEquals("04:30", domain.fajr)
        assertEquals("06:00", domain.sunrise)
        assertEquals("12:15", domain.dhuhr)
        assertEquals("15:30", domain.asr)
        assertEquals("18:20", domain.maghrib)
        assertEquals("19:50", domain.isha)
        assertEquals("04:20", domain.imsak)
        assertEquals("00:00", domain.midnight)
    }

    @Test
    fun `NextPrayerDto toDomain maps prayer name and cleans time string`() {
        val dateDto = DateDto(
            readable = "30 Mar 2026",
            timestamp = "1774828800",
            gregorian = GregorianDateDto(
                date = "30-03-2026",
                day = "30",
                weekday = WeekdayEnDto(en = "Monday"),
                month = GregorianMonthDto(number = 3, en = "March"),
                year = "2026"
            ),
            hijri = HijriDateDto(
                date = "11-10-1447",
                day = "11",
                weekday = WeekdayArDto(en = "Monday", ar = "الاثنين"),
                month = HijriMonthDto(number = 10, en = "Shawwal", ar = "شوال", days = 30),
                year = "1447"
            )
        )

        val dto = NextPrayerDto(
            timings = mapOf("Fajr" to "04:30 (+03)"),
            date = dateDto
        )

        val domain = dto.toDomain(dateStr = "2026-03-30")

        assertEquals("Fajr", domain.name)
        assertEquals("04:30", domain.time)
        assertEquals("2026-03-30", domain.date)
    }
}
