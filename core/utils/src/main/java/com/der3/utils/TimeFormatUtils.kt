package com.der3.utils

import java.util.Locale

object TimeFormatUtils {

    fun formatTime(time: String, is24Hour: Boolean): String {
        return if (is24Hour) {
            convertTo24Hour(time)
        } else {
            convertTo12Hour(time)
        }
    }

    private fun convertTo24Hour(time: String): String {
        val isPm = time.contains("م") || time.contains("PM")
        val isAm = time.contains("ص") || time.contains("AM")
        val cleanTime = time.replace(" م", "").replace(" ص", "")
            .replace(" PM", "").replace(" AM", "")
            .trim()
        val parts = cleanTime.split(":")
        if (parts.size < 2) return time
        var hour = parts[0].toIntOrNull() ?: return time
        val min = parts[1]

        if (isPm && hour != 12) hour += 12
        else if (isAm && hour == 12) hour = 0

        return String.format(Locale.ENGLISH, "%02d:%s", hour, min)
    }

    private fun convertTo12Hour(time: String): String {
        val isPm = time.contains("م") || time.contains("PM")
        val isAm = time.contains("ص") || time.contains("AM")

        val cleanTime = time.replace(" م", "").replace(" ص", "")
            .replace(" PM", "").replace(" AM", "")
            .trim()
        val parts = cleanTime.split(":")
        if (parts.size < 2) return time
        var hour = parts[0].toIntOrNull() ?: return time
        val min = parts[1]

        if (isPm || isAm) {
            val h = if (hour == 0) 12 else hour
            val s = if (isPm) "م" else "ص"
            return String.format(Locale.ENGLISH, "%02d:%s %s", h, min, s)
        }

        val suffix = if (hour >= 12) "م" else "ص"
        if (hour > 12) hour -= 12
        if (hour == 0) hour = 12

        return String.format(Locale.ENGLISH, "%02d:%s %s", hour, min, suffix)
    }
}
