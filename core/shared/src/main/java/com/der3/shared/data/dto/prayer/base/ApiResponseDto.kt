package com.der3.shared.data.dto.prayer.base

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto<T>(
    val code: Int,
    val status: String,
    val data: T
)