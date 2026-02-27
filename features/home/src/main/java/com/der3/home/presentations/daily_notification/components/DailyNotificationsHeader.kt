package com.der3.home.presentations.daily_notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun DailyNotificationsHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector = Icons.Default.Notifications,
    iconColor: Color = AppColors.green800,
    iconBgColor: Color = AppColors.green50,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(74.dp)
                .background(
                    color = iconBgColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(34.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = title,
            color = AppColors.green800,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = subtitle,
            color = AppColors.gray500,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(14.dp))
    }
}


@Preview(showBackground = true, name = "Top App Bar with Action")
@Composable
fun Der3TopAppBarPreviewWithAction() {
    Der3MuslimTheme {
        DailyNotificationsHeader(
            title = "التنبيهات اليومية",
            subtitle = "حافظ على وردك اليومي من خلال التنبيهات",
            icon = Icons.Default.Notifications,
            iconColor = AppColors.green800,
            iconBgColor = AppColors.green50,
        )
    }
}