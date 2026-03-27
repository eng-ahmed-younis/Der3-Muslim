package com.der3.sections.domain.model

data class NextPrayerInfoExtended(
    val nextPrayer: NextPrayerInfo,
    val currentPrayer: String? = null,
    val currentPrayerTime: String? = null,
    val status: PrayerStatus = PrayerStatus.UPCOMING,
    val allPrayers: List<PrayerStatusInfo> = emptyList()
)

