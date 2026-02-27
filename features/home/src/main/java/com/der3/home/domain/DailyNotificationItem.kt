package com.der3.home.domain

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.utils.PalletColors

data class DailyNotificationItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val timeText: String, // "06:00 ص"
    val enabled: Boolean,
    val icon: ImageVector,
    val iconBg: Color
)


val mockItems = listOf(
    DailyNotificationItem(
        id = "1",
        title = "أذكار الصباح",
        subtitle = "تبدأ عند شروق الشمس",
        icon = Icons.Default.WbSunny,
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "06:00 AM",
        enabled = true
    ),
    DailyNotificationItem(
        id = "2",
        title = "أذكار المساء",
        subtitle = "تبدأ عند غروب الشمس",
        icon = Icons.Default.NightsStay,
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "06:15 PM",
        enabled = false
    ),
    DailyNotificationItem(
        id = "3",
        title = "قيام الليل",
        subtitle = "قبل أذان الفجر",
        icon = Icons.Default.DarkMode,
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "04:30 AM",
        enabled = true
    ),
    DailyNotificationItem(
        id = "4",
        title = "صلاة الجمعة",
        subtitle = "تذكير قبل الأذان",
        icon = Icons.Default.Mosque,
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "12:00 PM",
        enabled = false
    )
)
