package com.der3.sections.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar


@Parcelize
data class NextPrayerInfo(
    val prayerName: String,
    val prayerTime: String,
    val prayerTimeAmPm: String,
    val gregorianDate: String,
    val hijriDate: String,
    val timestamp: Long = System.currentTimeMillis(),
    val latitude: Double? = null,
    val longitude: Double? = null,
    val timezone: String? = null,
    val methodId: Int? = null
) : Parcelable {

    // Computed properties
    val remainingTime: String
        get() = calculateRemainingTime()

    val remainingMinutes: Int
        get() = (calculateRemainingSeconds() / 60).toInt()

    val isPassed: Boolean
        get() = checkIfPassed()

    val prayerOrder: Int
        get() = when (prayerName.lowercase()) {
            "fajr" -> 1
            "sunrise" -> 2
            "dhuhr" -> 3
            "asr" -> 4
            "maghrib" -> 5
            "isha" -> 6
            else -> 0
        }

    val formattedRemainingTime: String
        get() = when {
            remainingMinutes <= 0 -> "Now"
            remainingMinutes < 60 -> "${remainingMinutes}m"
            else -> {
                val hours = remainingMinutes / 60
                val mins = remainingMinutes % 60
                if (mins > 0) "${hours}h ${mins}m" else "${hours}h"
            }
        }

    val nextPrayerIndex: Int
        get() = prayerOrder

    // Private helper methods
    private fun calculateRemainingSeconds(): Long {
        val now = Calendar.getInstance()
        val currentMillis = now.timeInMillis

        val timeParts = prayerTime.split(":")
        if (timeParts.size != 2) return -1

        val prayerCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            set(Calendar.MINUTE, timeParts[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (prayerCalendar.before(now)) {
            prayerCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return (prayerCalendar.timeInMillis - currentMillis) / 1000
    }

    private fun calculateRemainingTime(): String {
        val totalSeconds = calculateRemainingSeconds()
        return if (totalSeconds <= 0) {
            "00:00:00"
        } else {
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            String.format(java.util.Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds)
        }
    }

    private fun checkIfPassed(): Boolean {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

        val timeParts = prayerTime.split(":")
        if (timeParts.size != 2) return false

        val prayerMinutes = try {
            timeParts[0].toInt() * 60 + timeParts[1].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        return currentMinutes > prayerMinutes
    }

    companion object {
        fun empty(): NextPrayerInfo {
            return NextPrayerInfo(
                prayerName = "",
                prayerTime = "",
                prayerTimeAmPm = "",
                gregorianDate = "",
                hijriDate = ""
            )
        }
    }
}