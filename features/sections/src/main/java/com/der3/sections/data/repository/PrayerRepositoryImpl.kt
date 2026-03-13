package com.der3.sections.data.repository

import android.util.Log
import com.der3.sections.data.mappers.toNextPrayerInfo
import com.der3.sections.data.service.api.PrayerTimeService
import com.der3.sections.domain.model.NextPrayerInfo
import com.der3.sections.domain.model.PrayerStatus
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.data.dto.prayer.base.ApiResponseDto
import com.der3.shared.data.dto.prayer.timings.TimingsDto
import com.der3.shared.data.mappers.toDomain
import com.der3.shared.data.mappers.toDomainMethods
import com.der3.shared.domain.model.prayer.PrayerMethod
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PrayerRepositoryImpl"


@Singleton
class PrayerRepositoryImpl @Inject constructor(
    private val service: PrayerTimeService
) : IPrayerRepository {

    // Cache configuration
    private val cache = ConcurrentHashMap<String, Any>()
    private val cacheTimestamp = ConcurrentHashMap<String, Long>()
    private val cacheExpiryTime = 5 * 60 * 1000L // 5 minutes
    private val calendarCacheExpiryTime = 60 * 60 * 1000L // 1 hour for calendars

    //region ==================== BASIC PRAYER TIMES ====================

    override suspend fun getTodayPrayerTimes(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<PrayerTimes> {
        return getPrayerTimesByDate("today", latitude, longitude, method, school)
    }

    override suspend fun getPrayerTimesByDate(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<PrayerTimes> {
        val cacheKey = "prayer_${date}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<PrayerTimes>(cacheKey)?.let {
            Log.d(TAG, "Returning cached prayer times for $cacheKey")
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getPrayerTimesByLocation(
                    latitude = latitude,
                    longitude = longitude,
                    date = date,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { dto ->
                dto.toDomain().also { prayerTimes ->
                    cache[cacheKey] = prayerTimes
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get prayer times for $date"
        )
    }

    override suspend fun getPrayerTimesByCity(
        city: String,
        country: String,
        state: String?,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<PrayerTimes> {
        val cacheKey = "prayer_city_${city}_${country}_${state}_${method}_${school}"

        getCachedData<PrayerTimes>(cacheKey)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getPrayerTimesByCity(
                    city = city,
                    country = country,
                    state = state,
                    date = "today",
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { dto ->
                dto.toDomain().also { prayerTimes ->
                    cache[cacheKey] = prayerTimes
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get prayer times for $city, $country"
        )
    }

    override suspend fun getPrayerTimesByAddress(
        address: String,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<PrayerTimes> {
        val cacheKey = "prayer_address_${address}_${method}_${school}"

        getCachedData<PrayerTimes>(cacheKey)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getPrayerTimesByAddress(
                    address = address,
                    date = "today",
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { dto ->
                dto.toDomain().also { prayerTimes ->
                    cache[cacheKey] = prayerTimes
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get prayer times for address: $address"
        )
    }

    //endregion

    //region ==================== CALENDAR METHODS ====================

    override suspend fun getMonthlyCalendarByLocation(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<List<PrayerTimes>> {
        val cacheKey = "monthly_${year}_${month}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<List<PrayerTimes>>(cacheKey, calendarCacheExpiryTime)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getMonthlyCalendarByLocation(
                    year = year,
                    month = month,
                    latitude = latitude,
                    longitude = longitude,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { list ->
                list.map { it.toDomain() }.also { prayerTimesList ->
                    cache[cacheKey] = prayerTimesList
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get monthly calendar for $year-$month"
        )
    }

    override suspend fun getHijriMonthlyCalendar(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<List<PrayerTimes>> {
        val cacheKey = "hijri_monthly_${year}_${month}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<List<PrayerTimes>>(cacheKey, calendarCacheExpiryTime)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getHijriMonthlyCalendar(
                    year = year,
                    month = month,
                    latitude = latitude,
                    longitude = longitude,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { list ->
                list.map { it.toDomain() }.also { prayerTimesList ->
                    cache[cacheKey] = prayerTimesList
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get Hijri monthly calendar for $year-$month"
        )
    }

    override suspend fun getYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<Map<String, List<PrayerTimes>>> {
        val cacheKey = "yearly_${year}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<Map<String, List<PrayerTimes>>>(cacheKey, calendarCacheExpiryTime)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getYearlyCalendar(
                    year = year,
                    latitude = latitude,
                    longitude = longitude,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { map ->
                map.mapValues { (_, list) ->
                    list.map { it.toDomain() }
                }.also { yearlyMap ->
                    cache[cacheKey] = yearlyMap
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get yearly calendar for $year"
        )
    }

    override suspend fun getHijriYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<Map<String, List<PrayerTimes>>> {
        val cacheKey = "hijri_yearly_${year}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<Map<String, List<PrayerTimes>>>(cacheKey, calendarCacheExpiryTime)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getHijriYearlyCalendar(
                    year = year,
                    latitude = latitude,
                    longitude = longitude,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { map ->
                map.mapValues { (_, list) ->
                    list.map { it.toDomain() }
                }.also { yearlyMap ->
                    cache[cacheKey] = yearlyMap
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get Hijri yearly calendar for $year"
        )
    }

    override suspend fun getPrayerTimesForDateRange(
        startDate: String,
        endDate: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): IPrayerRepository.Result<List<PrayerTimes>> {
        val cacheKey = "range_${startDate}_${endDate}_${latitude}_${longitude}_${method}_${school}"

        getCachedData<List<PrayerTimes>>(cacheKey)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = {
                service.getPrayerTimesForDateRange(
                    startDate = startDate,
                    endDate = endDate,
                    latitude = latitude,
                    longitude = longitude,
                    method = method,
                    school = school,
                    tune = null,
                    timezone = null,
                    iso8601 = false
                )
            },
            mapper = { list ->
                list.map { it.toDomain() }.also { prayerTimesList ->
                    cache[cacheKey] = prayerTimesList
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get prayer times for range $startDate to $endDate"
        )
    }

    //endregion

    //region ==================== NEXT PRAYER METHODS ====================

    override fun observeNextPrayer(
        latitude: Double,
        longitude: Double,
        method: Int
    ): Flow<IPrayerRepository.Result<NextPrayerInfo>> = flow {
        while (true) {
            try {
                val response = service.getNextPrayer(
                    latitude = latitude,
                    longitude = longitude,
                    date = "today",
                    method = method,
                    school = 0,
                    tune = null,
                    timezone = null
                )

                when (response.code) {
                    200 -> {
                        val nextPrayerInfo = response.data.toNextPrayerInfo(
                            latitude = latitude,
                            longitude = longitude,
                            methodId = method
                        )
                        emit(IPrayerRepository.Result.Success(nextPrayerInfo))
                    }
                    else -> {
                        emit(IPrayerRepository.Result.Error(
                            message = "API Error: ${response.status}",
                            code = response.code
                        ))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error observing next prayer", e)
                emit(IPrayerRepository.Result.Error(
                    message = e.message ?: "Network error occurred",
                    throwable = e
                ))
            }

            delay(60_000) // Update every minute
        }
    }

    override suspend fun getCurrentPrayer(
        latitude: Double,
        longitude: Double,
        method: Int
    ): IPrayerRepository.Result<IPrayerRepository.CurrentPrayerInfo> {
        return try {
            val response = service.getPrayerTimesByLocation(
                latitude = latitude,
                longitude = longitude,
                date = "today",
                method = method,
                school = 0,
                tune = null,
                timezone = null,
                iso8601 = false
            )

            if (response.code == 200) {
                val timings = response.data.timings
                val currentPrayer = findCurrentPrayer(timings)

                currentPrayer?.let {
                    IPrayerRepository.Result.Success(it)
                } ?: IPrayerRepository.Result.Error("Could not determine current prayer")
            } else {
                IPrayerRepository.Result.Error("API Error: ${response.status}", response.code)
            }
        } catch (e: Exception) {
            IPrayerRepository.Result.Error(e.message ?: "Network error", throwable = e)
        }
    }

    override suspend fun getAllPrayersWithStatus(
        latitude: Double,
        longitude: Double,
        method: Int
    ): IPrayerRepository.Result<List<IPrayerRepository.PrayerWithStatus>> {
        return try {
            val response = service.getPrayerTimesByLocation(
                latitude = latitude,
                longitude = longitude,
                date = "today",
                method = method,
                school = 0,
                tune = null,
                timezone = null,
                iso8601 = false
            )

            if (response.code == 200) {
                val prayers = getPrayersWithStatus(response.data.timings)
                IPrayerRepository.Result.Success(prayers)
            } else {
                IPrayerRepository.Result.Error("API Error: ${response.status}", response.code)
            }
        } catch (e: Exception) {
            IPrayerRepository.Result.Error(e.message ?: "Network error", throwable = e)
        }
    }

    //endregion

    //region ==================== METHODS AND CONFIGURATION ====================

    override suspend fun getCalculationMethods(): IPrayerRepository.Result<List<PrayerMethod>> {
        val cacheKey = "calculation_methods"

        getCachedData<List<PrayerMethod>>(cacheKey, 24 * 60 * 60 * 1000L)?.let {
            return IPrayerRepository.Result.Success(it)
        }

        return safeApiCall(
            call = { service.getCalculationMethods() },
            mapper = { map ->
                map.toDomainMethods().also { methods ->
                    cache[cacheKey] = methods
                    cacheTimestamp[cacheKey] = System.currentTimeMillis()
                }
            },
            errorMessage = "Failed to get calculation methods"
        )
    }

    //endregion

    //region ==================== CACHE MANAGEMENT ====================

    override fun clearCache() {
        cache.clear()
        cacheTimestamp.clear()
        Log.d(TAG, "Cache cleared")
    }

    override fun close() {
        clearCache()
    }

    /**
     * Retrieves data from the in-memory cache if it exists and has not expired.
     *
     * @param key The unique identifier for the cached data.
     * @param expiryTime The maximum allowed age of the cache in milliseconds.
     *                   Defaults to [cacheExpiryTime] (5 minutes).
     * @return The cached object cast to type [T], or null if not found or expired.
     */
    private fun <T> getCachedData(key: String, expiryTime: Long = cacheExpiryTime): T? {
        val cached = cache[key]
        val timestamp = cacheTimestamp[key]

        // Check if we have both the data and the time it was stored
        if (cached != null && timestamp != null) {
            val age = System.currentTimeMillis() - timestamp

            // Return data only if it is still within the freshness window
            if (age < expiryTime) {
                @Suppress("UNCHECKED_CAST")
                return cached as T
            } else {
                // Proactively clean up expired entries
                cache.remove(key)
                cacheTimestamp.remove(key)
                Log.d(TAG, "Cache expired for key: $key")
            }
        }
        return null
    }

    //endregion

    //region ==================== PRIVATE HELPER METHODS ====================

    private suspend fun <T, R> safeApiCall(
        call: suspend () -> ApiResponseDto<T>,
        mapper: (T) -> R,
        errorMessage: String
    ): IPrayerRepository.Result<R> {
        return try {
            val response = call()

            if (response.code == 200) {
                val data = mapper(response.data)
                IPrayerRepository.Result.Success(data)
            } else {
                Log.e(TAG, "API Error: ${response.status} (${response.code})")
                IPrayerRepository.Result.Error(
                    message = "${errorMessage}: ${response.status}",
                    code = response.code
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network error in safeApiCall", e)
            IPrayerRepository.Result.Error(
                message = "${errorMessage}: ${e.message}",
                throwable = e
            )
        }
    }

    private fun findCurrentPrayer(timings: TimingsDto): IPrayerRepository.CurrentPrayerInfo? {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

        val prayers = getPrayerList(timings)

        for (i in prayers.indices) {
            val (name, time) = prayers[i]
            val timeParts = time.split(":")
            if (timeParts.size != 2) continue

            val prayerMinutes = timeParts[0].toInt() * 60 + timeParts[1].toInt()

            if (prayerMinutes <= currentMinutes) {
                val nextPrayer = if (i + 1 < prayers.size) prayers[i + 1] else prayers[0]
                val nextTime = nextPrayer.second
                val endsAt = nextTime
                val remainingTime = calculateTimeDifference(currentMinutes, nextTime)

                return IPrayerRepository.CurrentPrayerInfo(
                    name = name,
                    time = time,
                    endsAt = endsAt,
                    remainingTime = remainingTime
                )
            }
        }

        val lastPrayer = prayers.last()
        val nextPrayer = prayers.first()
        return IPrayerRepository.CurrentPrayerInfo(
            name = lastPrayer.first,
            time = lastPrayer.second,
            endsAt = nextPrayer.second,
            remainingTime = calculateTimeDifference(currentMinutes, nextPrayer.second)
        )
    }

    private fun getPrayersWithStatus(timings: TimingsDto): List<IPrayerRepository.PrayerWithStatus> {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

        val prayers = getPrayerList(timings)
        val prayersWithStatus = mutableListOf<IPrayerRepository.PrayerWithStatus>()

        var nextPrayerIndex = -1
        for (i in prayers.indices) {
            val (_, time) = prayers[i]
            val timeParts = time.split(":")
            if (timeParts.size != 2) continue

            val prayerMinutes = timeParts[0].toInt() * 60 + timeParts[1].toInt()

            if (prayerMinutes > currentMinutes) {
                nextPrayerIndex = i
                break
            }
        }

        if (nextPrayerIndex == -1) {
            nextPrayerIndex = 0
        }

        prayers.forEachIndexed { index, (name, time) ->
            val timeParts = time.split(":")
            val prayerMinutes = timeParts[0].toInt() * 60 + timeParts[1].toInt()

            val status = when {
                index == nextPrayerIndex - 1 && prayerMinutes <= currentMinutes ->
                    PrayerStatus.CURRENT
                index == nextPrayerIndex ->
                    PrayerStatus.UPCOMING
                index < nextPrayerIndex ->
                    PrayerStatus.COMPLETED
                index > nextPrayerIndex ->
                    PrayerStatus.UPCOMING
                else ->
                    PrayerStatus.COMPLETED
            }

            val timeRemaining = if (status == PrayerStatus.UPCOMING || status == PrayerStatus.CURRENT) {
                calculateTimeDifference(currentMinutes, time)
            } else null

            prayersWithStatus.add(
                IPrayerRepository.PrayerWithStatus(
                    name = name,
                    time = time,
                    status = status,
                    timeRemaining = timeRemaining
                )
            )
        }

        return prayersWithStatus
    }

    private fun getPrayerList(timings: TimingsDto): List<Pair<String, String>> {
        return listOf(
            "Fajr" to timings.fajr.replace(" (UTC)", ""),
            "Sunrise" to timings.sunrise.replace(" (UTC)", ""),
            "Dhuhr" to timings.dhuhr.replace(" (UTC)", ""),
            "Asr" to timings.asr.replace(" (UTC)", ""),
            "Maghrib" to timings.maghrib.replace(" (UTC)", ""),
            "Isha" to timings.isha.replace(" (UTC)", "")
        )
    }

    private fun calculateTimeDifference(currentMinutes: Int, prayerTime: String): String {
        val timeParts = prayerTime.split(":")
        if (timeParts.size != 2) return "0m"

        val prayerMinutes = timeParts[0].toInt() * 60 + timeParts[1].toInt()

        var diff = prayerMinutes - currentMinutes
        if (diff < 0) diff += 24 * 60

        val hours = diff / 60
        val minutes = diff % 60

        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            else -> "${minutes}m"
        }
    }

    //endregion

    //region ==================== ADDITIONAL UTILITY METHODS ====================

    suspend fun getPrayerTimesForMultipleLocations(
        locations: List<Pair<Double, Double>>,
        method: Int = 2
    ): IPrayerRepository.Result<Map<Pair<Double, Double>, PrayerTimes>> {
        val results = mutableMapOf<Pair<Double, Double>, PrayerTimes>()
        val errors = mutableListOf<String>()

        locations.forEach { (lat, lng) ->
            when (val result = getTodayPrayerTimes(lat, lng, method, 0)) {
                is IPrayerRepository.Result.Success -> {
                    results[lat to lng] = result.data
                }
                is IPrayerRepository.Result.Error -> {
                    errors.add("$lat,$lng: ${result.message}")
                }
                else -> {}
            }
        }

        return if (errors.isEmpty()) {
            IPrayerRepository.Result.Success(results)
        } else {
            IPrayerRepository.Result.Error(
                message = "Failed for some locations: ${errors.joinToString(", ")}"
            )
        }
    }

    suspend fun searchCities(query: String): IPrayerRepository.Result<List<CitySuggestion>> {
        return IPrayerRepository.Result.Success(emptyList())
    }

    suspend fun getPrayerTimesWithAutoMethod(
        latitude: Double,
        longitude: Double
    ): IPrayerRepository.Result<PrayerTimes> {
        val method = detectMethodFromLocation(latitude, longitude)
        return getTodayPrayerTimes(latitude, longitude, method, 0)
    }

    private fun detectMethodFromLocation(latitude: Double, longitude: Double): Int {
        return when {
            latitude in 22.0..32.0 && longitude in 25.0..35.0 -> 5
            latitude in 16.0..32.0 && longitude in 34.0..55.0 -> 4
            latitude in 36.0..42.0 && longitude in 26.0..45.0 -> 13
            latitude > 48.0 && longitude in -10.0..20.0 -> 2
            else -> 3
        }
    }

    //endregion
}

// Additional data classes for extended functionality
data class CitySuggestion(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)