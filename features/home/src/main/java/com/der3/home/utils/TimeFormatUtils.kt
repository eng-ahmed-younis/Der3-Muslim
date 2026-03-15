package com.der3.home.utils


import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeFormatUtils {

    fun getRelativeTimeSpanString(timeMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timeMillis
        val diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diff)
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            diffInSeconds < 60 -> "منذ ثوانٍ"
            diffInMinutes < 60 -> {
                when (diffInMinutes) {
                    1L -> "منذ دقيقة"
                    2L -> "منذ دقيقتين"
                    in 3..10 -> "منذ $diffInMinutes دقائق"
                    else -> "منذ $diffInMinutes دقيقة"
                }
            }
            diffInHours < 24 -> {
                when (diffInHours) {
                    1L -> "منذ ساعة"
                    2L -> "منذ ساعتين"
                    in 3..10 -> "منذ $diffInHours ساعات"
                    else -> "منذ $diffInHours ساعة"
                }
            }
            diffInDays < 30 -> {
                when (diffInDays) {
                    1L -> "منذ يوم"
                    2L -> "منذ يومين"
                    in 3..10 -> "منذ $diffInDays أيام"
                    else -> "منذ $diffInDays يوم"
                }
            }
            diffInDays < 365 -> {
                val months = diffInDays / 30
                when (months) {
                    1L -> "منذ شهر"
                    2L -> "منذ شهرين"
                    in 3..10 -> "منذ $months أشهر"
                    else -> "منذ $months شهر"
                }
            }
            else -> {
                val years = diffInDays / 365
                when (years) {
                    1L -> "منذ سنة"
                    2L -> "منذ سنتين"
                    in 3..10 -> "منذ $years سنوات"
                    else -> "منذ $years سنة"
                }
            }
        }
    }

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

