package com.der3.sections.domain.model

import com.der3.sections.domain.repository.IPrayerRepository


data class CombinedPrayerInfo(
    val nextPrayer: NextPrayerInfo,
    val allPrayers: List<IPrayerRepository.PrayerWithStatus>
) {
    val currentPrayer: IPrayerRepository.PrayerWithStatus?
        get() = allPrayers.find { it.status == PrayerStatus.CURRENT }

    val upcomingPrayers: List<IPrayerRepository.PrayerWithStatus>
        get() = allPrayers.filter { it.status == PrayerStatus.UPCOMING }

    val completedPrayers: List<IPrayerRepository.PrayerWithStatus>
        get() = allPrayers.filter { it.status == PrayerStatus.COMPLETED }
}