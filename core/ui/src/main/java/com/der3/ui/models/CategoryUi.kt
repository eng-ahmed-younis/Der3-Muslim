package com.der3.ui.models


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.utils.PalletColors

data class CategoryUi(
    val id: Int,
    val title: String,
    val subtitle: String,
    val count: String = "",
    val icon: ImageVector
){
    val iconTint: Color = PalletColors.iconBackgroundColors.random()
    val iconBg: Color = iconTint.copy(alpha = 0.3f)
}
