package com.der3.shared.data.dto.prayer.meta

import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val method: MethodDto
)
