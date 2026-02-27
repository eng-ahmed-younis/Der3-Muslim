package com.der3.model

data class AzkarItemUiModel(
    val id: Int,
    val text: String,
    val count: Int,
    val audioPath: String,
    val isPlaying: Boolean = false,
    val currentCount: Int = 0
)