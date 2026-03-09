package com.der3.shared.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MasbahaAzkarDto(
    val id: Int = 0,
    val text: String = "",
    val count: Int? = null
)