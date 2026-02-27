package com.der3.home.presentations.daily_notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.home.domain.DailyNotificationItem
import com.der3.ui.themes.AppColors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.utils.PalletColors


@Composable
fun DailyNotificationCard(
    modifier: Modifier = Modifier,
    item: DailyNotificationItem,
    onToggle: (Boolean) -> Unit,
    onEditTime: () -> Unit
) {
    val borderColor = if (item.enabled) AppColors.gold600 else Color.Transparent

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.white),
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, RoundedCornerShape(24.dp)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Toggle (left)
                Switch(
                    checked = item.enabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = AppColors.green800,
                        checkedThumbColor = AppColors.white,

                        uncheckedTrackColor = AppColors.gray200,
                        uncheckedThumbColor = AppColors.white
                    )
                )

                Spacer(Modifier.width(10.dp))

                // Title + subtitle (center)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.gray900Text,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.subtitle,
                        color = AppColors.gray500,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.width(12.dp))

                // Right icon bubble
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(item.iconBg, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = AppColors.gray900Text
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(fraction = .9f),
                color = AppColors.gray100
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Edit time (left)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = onEditTime)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = AppColors.gold600,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "تعديل الوقت",
                        color = AppColors.gold600,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                // time (right)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = AppColors.green800,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = item.timeText,
                        color = AppColors.green800,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF3F4F6)
@Composable
private fun DailyNotificationCardPreview() {
    val enabledItem = DailyNotificationItem(
        id = "1",
        title = "أذكار الصباح",
        subtitle = "تبدأ عند شروق الشمس",
        icon = Icons.Default.WbSunny,
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "06:00 AM",
        enabled = true
    )

    val disabledItem = DailyNotificationItem(
        id = "2",
        title = "أذكار المساء",
        subtitle = "تبدأ عند غروب الشمس",
        icon = Icons.Default.NightsStay, // Using a different icon for variety
        iconBg = PalletColors.iconBackgroundColors.random(),
        timeText = "06:15 PM",
        enabled = false
    )

    Der3MuslimTheme {
        // Use a LazyColumn to provide a realistic background color and padding
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DailyNotificationCard(
                    item = enabledItem,
                    onToggle = {},
                    onEditTime = {}
                )
            }
            item {
                DailyNotificationCard(
                    item = disabledItem,
                    onToggle = {},
                    onEditTime = {}
                )
            }
        }
    }
}


