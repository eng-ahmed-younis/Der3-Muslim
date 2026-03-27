package com.der3.shared.data.dto.prayer.timings

import kotlinx.serialization.Serializable


@Serializable
data class GregorianMonthDto(
    val number: Int,
    val en: String
)