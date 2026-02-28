package com.der3.home.domain

data class ZekrUiModel(
    val id: Int,
    val text: String,
    val translation: String,
    val source: String,
    val repeatCount: Int,
    val isFavorite: Boolean
)