package com.der3.ui.models


import androidx.annotation.Keep
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
data class CategoryUi(
    val id: Int = -1,
    val title: String  = "",
    val subtitle: String = "",
    val count: String = "",
    val icon: ImageVector = Icons.Default.WbSunny
){
    val iconTint: Color = PalletColors.iconBackgroundColors.random()
    val iconBg: Color = iconTint.copy(alpha = 0.3f)
}
