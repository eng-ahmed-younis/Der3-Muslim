package com.der3.home.domain.model

data class DeletedZekrUiModel(
    val zekr: ZekrUiModel,
    val deletedSince: String
)