package com.der3.home.domain.model

data class ZekrUiModel(
    val id: Int,
    val text: String,
    val audioPath: String,
    val category: String? = null,
    val repeatCount: Int,
    val isFavorite: Boolean = false,
    val isBookmarked: Boolean = false
){
    companion object {
        val mock = ZekrUiModel(
            id = -1,
            text = "mock",
            audioPath = "mock",
            repeatCount = -1,
            isFavorite = false,
            category = "mock",
            isBookmarked = false
        )
    }
}


