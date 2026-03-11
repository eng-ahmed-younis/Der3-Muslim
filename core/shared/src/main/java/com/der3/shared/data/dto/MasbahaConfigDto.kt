package com.der3.shared.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MasbahaConfigDto(
    val version: Int = 0
)