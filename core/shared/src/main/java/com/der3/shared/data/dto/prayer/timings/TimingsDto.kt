package com.der3.shared.data.dto.prayer.timings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimingsDto(
    @SerialName("Fajr") val fajr: String,
    @SerialName("Sunrise") val sunrise: String,
    @SerialName("Dhuhr") val dhuhr: String,
    @SerialName("Asr") val asr: String,
    @SerialName("Maghrib") val maghrib: String,
    @SerialName("Isha") val isha: String,
    @SerialName("Imsak") val imsak: String,
    @SerialName("Midnight") val midnight: String,
    @SerialName("Firstthird") val firstThird: String? = null,
    @SerialName("Lastthird") val lastThird: String? = null
)
