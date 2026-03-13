package com.der3.shared.data.dto.prayer.base

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MethodsResponseDto(
    val code: Int,
    val status: String,
    val data: Map<String, MethodDetailDto>
)

@Serializable
data class MethodDetailDto(
    val id: Int,
    val name: String,
    val params: Map<String, JsonElement>
)
