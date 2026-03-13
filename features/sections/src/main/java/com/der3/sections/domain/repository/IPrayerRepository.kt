package com.der3.sections.domain.repository

import com.der3.sections.domain.model.NextPrayerInfo
import com.der3.sections.domain.model.PrayerStatus
import com.der3.shared.domain.model.prayer.PrayerMethod
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow

interface IPrayerRepository {

    /**
     * Get prayer times for today by coordinates
     */
    suspend fun getTodayPrayerTimes(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<PrayerTimes>

    /**
     * Get prayer times for a specific date by coordinates
     */
    suspend fun getPrayerTimesByDate(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<PrayerTimes>

    /**
     * Get prayer times by city name
     */
    suspend fun getPrayerTimesByCity(
        city: String,
        country: String,
        state: String?,
        method: Int,
        school: Int
    ): Result<PrayerTimes>

    /**
     * Get prayer times by address
     */
    suspend fun getPrayerTimesByAddress(
        address: String,
        method: Int,
        school: Int
    ): Result<PrayerTimes>

    /**
     * Get monthly calendar for Gregorian month by location
     */
    suspend fun getMonthlyCalendarByLocation(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<List<PrayerTimes>>

    /**
     * Get monthly calendar for Hijri month
     */
    suspend fun getHijriMonthlyCalendar(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<List<PrayerTimes>>

    /**
     * Observe next prayer time (updates every minute)
     */
    fun observeNextPrayer(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int = 0
    ): Flow<Result<NextPrayerInfo>>

    /**
     * Get all available calculation methods
     */
    suspend fun getCalculationMethods(): Result<List<PrayerMethod>>

    /**
     * Get prayer times for a date range
     */
    suspend fun getPrayerTimesForDateRange(
        startDate: String,
        endDate: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<List<PrayerTimes>>

    /**
     * Get yearly calendar (Gregorian)
     */
    suspend fun getYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<Map<String, List<PrayerTimes>>>

    /**
     * Get yearly calendar (Hijri)
     */
    suspend fun getHijriYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Result<Map<String, List<PrayerTimes>>>

    /**
     * Clear cache if needed
     */
    fun clearCache()

    /**
     * Close repository and release resources
     */
    fun close()

    /**
     * Get current prayer info
     */
    suspend fun getCurrentPrayer(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int = 0
    ): Result<CurrentPrayerInfo>

    /**
     * Get all prayers with their status
     */
    suspend fun getAllPrayersWithStatus(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int = 0
    ): Result<List<PrayerWithStatus>>


    data class CurrentPrayerInfo(
        val name: String,
        val time: String,
        val endsAt: String,
        val remainingTime: String
    )

    data class PrayerWithStatus(
        val name: String,
        val time: String,
        val status: PrayerStatus, // UPCOMING, CURRENT, PASSED
        val timeRemaining: String? = null
    )

    sealed class Result<out T> {
        data class Success<T>(val data: T) : Result<T>()
        data class Error(
            val message: String,
            val code: Int? = null,
            val throwable: Throwable? = null
        ) : Result<Nothing>()

        object Loading : Result<Nothing>()

        fun isSuccess() = this is Success
        fun isError() = this is Error
        fun isLoading() = this is Loading

        fun getOrNull(): T? = (this as? Success)?.data
        fun errorMessage(): String? = (this as? Error)?.message
    }

}
