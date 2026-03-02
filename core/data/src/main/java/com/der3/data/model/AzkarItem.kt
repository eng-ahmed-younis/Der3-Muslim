package com.der3.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AzkarItem(
    val id: Int,
    val text: String,
    val count: Int,
    @SerialName(value = "audio")
    val audioPath: String,
    val filename: String
)