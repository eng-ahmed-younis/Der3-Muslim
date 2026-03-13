package com.der3.sections.data.service.impl


import com.der3.sections.data.service.api.PrayerTimeService
import com.der3.shared.BuildConfig
import com.der3.shared.data.dto.prayer.base.ApiResponseDto
import com.der3.shared.data.dto.prayer.base.MethodDetailDto
import com.der3.shared.data.dto.prayer.NextPrayerDto
import com.der3.shared.data.dto.prayer.timings.PrayerTimesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerTimeServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val baseUrl: String = BuildConfig.BASE_URL
) : PrayerTimeService {

    companion object {
        private const val TIMINGS_ENDPOINT = "timings"
        private const val TIMINGS_BY_CITY = "timingsByCity"
        private const val TIMINGS_BY_ADDRESS = "timingsByAddress"
        private const val CALENDAR_ENDPOINT = "calendar"
        private const val HIJRI_CALENDAR = "hijriCalendar"
        private const val NEXT_PRAYER = "nextPrayer"
        private const val METHODS_ENDPOINT = "methods"
        private const val DATE_RANGE_CALENDAR = "calendar/from"
    }

    override suspend fun getPrayerTimesByLocation(
        latitude: Double,
        longitude: Double,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(TIMINGS_ENDPOINT, date)
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString()) // Shafi (or the standard way) or Hanafi. Possible values: 0 - Shafi 1 - Hanafi ,Default: 0
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getPrayerTimesByCity(
        city: String,
        country: String,
        state: String?,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(TIMINGS_BY_CITY, date)
                parameters.apply {
                    append("city", city)
                    append("country", country)
                    state?.let { append("state", it) }
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getPrayerTimesByAddress(
        address: String,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<PrayerTimesDto> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(TIMINGS_BY_ADDRESS, date)
                parameters.apply {
                    append("address", address)
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getMonthlyCalendarByLocation(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(CALENDAR_ENDPOINT, year.toString(), month.toString())
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getHijriMonthlyCalendar(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(HIJRI_CALENDAR, year.toString(), month.toString())
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getNextPrayer(
        latitude: Double,
        longitude: Double,
        date: String,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?
    ): ApiResponseDto<NextPrayerDto> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(NEXT_PRAYER, date)
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                }
            }
        }.body()
    }

    override suspend fun getCalculationMethods(): ApiResponseDto<Map<String, MethodDetailDto>> =
        withContext(Dispatchers.IO) {
            client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = baseUrl.removePrefix("https://").removePrefix("http://")
                    appendPathSegments(METHODS_ENDPOINT)
                }
            }.body()
        }

    override suspend fun getPrayerTimesForDateRange(
        startDate: String,
        endDate: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<List<PrayerTimesDto>> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(DATE_RANGE_CALENDAR, startDate, "to", endDate)
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<Map<String, List<PrayerTimesDto>>> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(CALENDAR_ENDPOINT, year.toString())
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override suspend fun getHijriYearlyCalendar(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int,
        tune: String?,
        timezone: String?,
        iso8601: Boolean
    ): ApiResponseDto<Map<String, List<PrayerTimesDto>>> = withContext(Dispatchers.IO) {
        client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl.removePrefix("https://").removePrefix("http://")
                appendPathSegments(HIJRI_CALENDAR, year.toString())
                parameters.apply {
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                    append("method", method.toString())
                    append("school", school.toString())
                    tune?.let { append("tune", it) }
                    timezone?.let { append("timezonestring", it) }
                    append("iso8601", iso8601.toString())
                }
            }
        }.body()
    }

    override fun close() {
        client.close()
    }
}