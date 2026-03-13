package com.der3.sections.data.service.api

import com.der3.shared.data.dto.prayer.base.ApiResponseDto
import com.der3.shared.data.dto.prayer.base.MethodDetailDto
import com.der3.shared.data.dto.prayer.NextPrayerDto
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto

interface PrayerTimeService {
    /**
     * Get prayer times for a specific [date] by [latitude] and [longitude]
     */
    suspend fun getPrayerTimesByLocation(
        latitude: Double,
        longitude: Double,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto>

    /**
     * Get prayer times for a specific [date] by [city]
     */
    suspend fun getPrayerTimesByCity(
        city: String,
        country: String,
        state: String?,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto>

    /**
     * Get prayer times for a specific [date] by [address]
     */
    suspend fun getPrayerTimesByAddress(
        address: String,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto>

    /**
     * Get monthly calendar for Gregorian month by location
     */
    suspend fun getMonthlyCalendarByLocation(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>>

    /**
     * Get monthly calendar for Hijri month
     */
    suspend fun getHijriMonthlyCalendar(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>>

    /**
     * Get next prayer time
     */
    suspend fun getNextPrayer(
        latitude: Double,
        longitude: Double,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?
    ): ApiResponseDto<NextPrayerDto>

    /**
     * Get all calculation methods
     */
    suspend fun getCalculationMethods(): ApiResponseDto<Map<String, MethodDetailDto>>

    /**
     * Get prayer times for date range
     */
    suspend fun getPrayerTimesForDateRange(
        startDate: String,
        endDate: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>>

    /**
     * Get prayer times for Gregorian year
     */
    suspend fun getYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<Map<String, List<PrayerTimesDto>>>

    /**
     * Get prayer times for Hijri year
     */
    suspend fun getHijriYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<Map<String, List<PrayerTimesDto>>>

    /**
     * Close the service client
     */
    fun close()

}