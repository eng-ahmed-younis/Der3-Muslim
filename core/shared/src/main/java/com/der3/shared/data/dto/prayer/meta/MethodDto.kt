package com.der3.shared.data.dto.prayer.meta

import kotlinx.serialization.Serializable

@Serializable
data class MethodDto(
    val id: Int,
    val name: String
)
