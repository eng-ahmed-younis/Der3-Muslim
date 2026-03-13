package com.der3.sections.domain.model
import java.text.SimpleDateFormat
import java.util.*

class NextPrayerInfoBuilder {
    private var prayerName: String = ""
    private var prayerTime: String = ""
    private var gregorianDate: String = ""
    private var hijriDate: String = ""
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var timezone: String? = null
    private var methodId: Int? = null

    fun setPrayerName(name: String) = apply { this.prayerName = name }
    fun setPrayerTime(time: String) = apply { this.prayerTime = time }
    fun setGregorianDate(date: String) = apply { this.gregorianDate = date }
    fun setHijriDate(date: String) = apply { this.hijriDate = date }
    fun setCoordinates(lat: Double, lng: Double) = apply {
        this.latitude = lat
        this.longitude = lng
    }
    fun setTimezone(tz: String) = apply { this.timezone = tz }
    fun setMethodId(id: Int) = apply { this.methodId = id }

    fun build(): NextPrayerInfo {
        val amPmTime = convertToAmPm(prayerTime)

        return NextPrayerInfo(
            prayerName = prayerName,
            prayerTime = prayerTime,
            prayerTimeAmPm = amPmTime,
            gregorianDate = gregorianDate,
            hijriDate = hijriDate,
            latitude = latitude,
            longitude = longitude,
            timezone = timezone,
            methodId = methodId
        )
    }

    private fun convertToAmPm(time: String): String {
        return try {
            val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val date = inputFormat.parse(time)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            time
        }
    }
}