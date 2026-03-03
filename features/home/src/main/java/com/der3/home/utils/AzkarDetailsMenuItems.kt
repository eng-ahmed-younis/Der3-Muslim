package com.der3.home.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MoreVert
import com.der3.ui.models.MenuItemData
import com.der3.ui.models.MenuItemType

val menuItems = listOf(
    MenuItemData(
        id = AzkarDetailsMenuItems.ZEKR_FONT_SIZE,
        icon = Icons.Default.FormatSize,
        text = "حجم الخط"
    )
)

sealed interface AzkarDetailsMenuItems : MenuItemType {
    data object ZEKR_FONT_SIZE : AzkarDetailsMenuItems
}