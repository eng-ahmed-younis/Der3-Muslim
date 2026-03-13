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
        get() = calculateRemainingMinutes()

    val isPassed: Boolean
        get() = checkIfPassed()

    val prayerOrder: Int
        get() = getPrayerOrder()

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
    private fun calculateRemainingMinutes(): Int {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)

        val timeParts = prayerTime.split(":")
        if (timeParts.size != 2) return -1

        val prayerMinutes = try {
            timeParts[0].toInt() * 60 + timeParts[1].toInt()
        } catch (e: NumberFormatException) {
            return -1
        }

        var diff = prayerMinutes - currentMinutes
        if (diff < 0) {
            diff += 24 * 60 // Add 24 hours if prayer has passed today
        }

        return diff
    }

    private fun calculateRemainingTime(): String {
        val minutes = calculateRemainingMinutes()
        return when {
            minutes <= 0 -> "00:00"
            else -> {
                val hours = minutes / 60
                val mins = minutes % 60
                String.format("%02d:%02d", hours, mins)
            }
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

    private fun getPrayerOrder(): Int {
        return when (prayerName.lowercase()) {
            "fajr" -> 1
            "sunrise" -> 2
            "dhuhr" -> 3
            "asr" -> 4
            "maghrib" -> 5
            "isha" -> 6
            else -> 0
        }
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