package com.der3.home.domain

data class ZekrUiModel(
    val id: Int,
    val text: String,
    val sourceUrl: String,
    val repeatCount: Int,
    val isFavorite: Boolean = false,
    val isBookmarked: Boolean = false
){
    companion object {
        val mock = ZekrUiModel(
            id = -1,
            text = "mock",
            sourceUrl = "mock",
            repeatCount = -1,
            isFavorite = false,
            isBookmarked = false
        )
    }
}




