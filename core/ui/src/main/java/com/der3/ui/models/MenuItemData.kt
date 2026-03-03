package com.der3.ui.models

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    val id: MenuItemType? = null,
    val icon: ImageVector,
    val text: String = ""
)

interface MenuItemType