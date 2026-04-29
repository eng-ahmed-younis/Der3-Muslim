package com.der3.sections.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GeocoderPayloads(
    val latitude: Double,
    val longitude: Double,
    val address: String
)

