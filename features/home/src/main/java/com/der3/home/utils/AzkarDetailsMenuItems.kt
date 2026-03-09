package com.der3.home.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.VolumeUp
import com.der3.ui.models.MenuItemData
import com.der3.ui.models.MenuItemType

val menuItems = listOf(
    MenuItemData(
        id = AzkarDetailsMenuItems.ZEKR_FONT_SIZE,
        icon = Icons.Default.FormatSize,
        text = "حجم الخط"
    ),
    MenuItemData(
        id = AzkarDetailsMenuItems.ZEKR_VOLUME,
        icon = Icons.Default.VolumeUp,
        text = "مستوى الصوت"
    )
)

sealed interface AzkarDetailsMenuItems : MenuItemType {
    data object ZEKR_FONT_SIZE : AzkarDetailsMenuItems
    data object ZEKR_VOLUME : AzkarDetailsMenuItems
}