package com.der3.shared.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AzkarCategory(
    val id: Int,
    val category: String,
    val audio: String,
    val filename: String,
    @SerialName("array")
    val items: List<AzkarItem>
)