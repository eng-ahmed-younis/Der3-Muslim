package com.der3.data.params

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Serializable
@Keep
data class CategoryDetailsParams(
    val categoryId: Int,
    val categoryTitle: String,
    val categorySubtitle: String,
    val categoryCount: String
)

