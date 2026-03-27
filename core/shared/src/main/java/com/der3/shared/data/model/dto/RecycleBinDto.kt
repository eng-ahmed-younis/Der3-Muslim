package com.der3.shared.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecycleBinDto(
    val id: Int,
    val categoryId: Int,
    val text: String,
    val audioPath: String,
    val repeatCount: Int,
    val deletedAt: Long,
    val categoryName: String? = null
)
