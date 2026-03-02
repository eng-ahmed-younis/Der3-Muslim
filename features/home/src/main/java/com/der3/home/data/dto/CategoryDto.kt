package com.der3.home.data.dto

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import com.der3.ui.models.CategoryUi
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val title: String,
    val subtitle: String,
    val count: String,
    val iconKey: String
)


fun CategoryDto.toUi(): CategoryUi {
    return CategoryUi(
        id = id,
        title = title,
        subtitle = subtitle,
        count = count,
        icon = when (iconKey) {
            "sun" -> Icons.Default.WbSunny
            else -> Icons.Default.WbSunny
        }
    )
}