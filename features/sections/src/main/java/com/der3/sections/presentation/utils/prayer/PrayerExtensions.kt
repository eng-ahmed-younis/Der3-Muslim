package com.der3.sections.presentation.utils.prayer

import com.der3.sections.domain.model.PrayerType
import com.der3.ui.R

fun PrayerType.getPrayerIcon():Int {
    return when (this) {
        PrayerType.FAJR -> R.drawable.fajar
        PrayerType.SUNRISE -> R.drawable.dohor
        PrayerType.DHUHR -> R.drawable.dohor
        PrayerType.ASR -> R.drawable.asr
        PrayerType.MAGHRIB -> R.drawable.maghrib
        PrayerType.ISHA -> R.drawable.isha
        PrayerType.IMSAK -> R.drawable.isha // Fallback
        PrayerType.MIDNIGHT -> R.drawable.isha // Fallback
        PrayerType.FIRST_THIRD -> R.drawable.isha // Fallback
        PrayerType.LAST_THIRD -> R.drawable.isha // Fallback
    }
}