package com.der3.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AzkarItem(
    val id: Int,
    val text: String,
    val count: Int,
    val audio: String,
    val filename: String
)