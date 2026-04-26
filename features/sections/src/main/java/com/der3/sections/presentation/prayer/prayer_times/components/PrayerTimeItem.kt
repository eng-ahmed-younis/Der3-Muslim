package com.der3.sections.presentation.prayer.prayer_times.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.sections.domain.model.PrayerDetails
import com.der3.sections.domain.model.PrayerType
import com.der3.sections.presentation.utils.prayer.getPrayerIcon
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

/**
 * A composable that displays a single prayer time item, including the name, time, and notification toggle.
 *
 * @param prayer The details of the prayer to display.
 * @param onToggleNotification Callback to toggle the notification for this prayer.
 */
@Composable
fun PrayerTimeItem(
    prayer: PrayerDetails,
    onToggleNotification: () -> Unit
) {
    val isNext = prayer.isNext
    val isCurrent = prayer.isCurrent
    val isDark = isDarkTheme

    val backgroundColor = when {
        isNext && !isDark -> AppColors.green800
        isCurrent && !isDark -> AppColors.green50
        isDark -> AppColors.cardColor
        else -> AppColors.cardColor
    }

    val borderColor = when {
        isNext && isDark -> AppColors.gold700.copy(alpha = 0.5f)
        isCurrent && isDark -> AppColors.green700.copy(alpha = 0.5f)
        isNext && !isDark -> Color.Transparent
        isCurrent && !isDark -> AppColors.green400.copy(alpha = 0.5f)
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .then(
                if (isNext || isCurrent) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isNext && !isDark) 4.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Right side in RTL (Start)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = prayer.type.getPrayerIcon()),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent
                        )
                        .size(40.dp),
                    colorFilter = when {
                        isNext -> if (isDark) AppColors.gold400 else Color.White
                        isCurrent -> if (isDark) AppColors.green700 else AppColors.green800
                        prayer.isPassed -> AppColors.gray400
                        else -> AppColors.zekrPanelProgress
                    }.let { color -> ColorFilter.tint(color) }

                )

                Spacer(modifier = Modifier.width(12.dp))


                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            prayer.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                isNext -> if (isDark) Color.White else Color.White
                                isCurrent -> if (isDark) Color.White else AppColors.green800
                                prayer.isPassed -> AppColors.gray400
                                else -> AppColors.gray900Text
                            }
                        )
                        if (isCurrent) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = if (isDark) AppColors.green700.copy(alpha = 0.2f) else AppColors.green800.copy(
                                    alpha = 0.1f
                                ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "الآن",
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isDark) AppColors.green700 else AppColors.green800,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Text(
                        prayer.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = when {
                            isNext -> if (isDark) Color.White.copy(alpha = 0.7f) else Color.White.copy(
                                alpha = 0.7f
                            )

                            isCurrent -> if (isDark) AppColors.zekrSubText else AppColors.green800.copy(
                                alpha = 0.6f
                            )

                            else -> AppColors.zekrSubText
                        }
                    )
                }
            }

            // Left side in RTL (End)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    prayer.time.split(" ")[0],
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = when {
                        isNext -> if (isDark) AppColors.gold400 else Color.White
                        isCurrent -> if (isDark) AppColors.green700 else AppColors.green800
                        prayer.isPassed -> AppColors.gray400
                        else -> AppColors.zekrPanelProgress
                    }
                )

                IconButton(onClick = onToggleNotification) {
                    Icon(
                        imageVector = if (prayer.notificationEnabled) {
                            Icons.Default.Notifications
                        } else {
                            Icons.Default.NotificationsOff
                        },
                        contentDescription = null,
                        tint = when {
                            isNext -> if (isDark) AppColors.gold400 else Color.White
                            isCurrent -> if (isDark) AppColors.green700 else AppColors.green800
                            prayer.notificationEnabled -> if (isDark) AppColors.gold400 else AppColors.green800
                            else -> AppColors.gray400
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun PrayerTimeItemPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrayerTimeItem(
                prayer = PrayerDetails("الفجر", "04:42 ص", isPassed = true, type = PrayerType.FAJR),
                onToggleNotification = {}
            )
            PrayerTimeItem(
                prayer = PrayerDetails("الظهر", "12:05 م", isNext = true, type = PrayerType.DHUHR),
                onToggleNotification = {}
            )
            PrayerTimeItem(
                prayer = PrayerDetails("العصر", "03:45 م", type = PrayerType.ASR),
                onToggleNotification = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PrayerTimeItemDarkPreview() {
    Der3MuslimTheme(
        style = com.der3.model.AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrayerTimeItem(
                prayer = PrayerDetails("الفجر", "04:42 ص", isPassed = true, type = PrayerType.FAJR),
                onToggleNotification = {}
            )
            PrayerTimeItem(
                prayer = PrayerDetails("الظهر", "12:05 م", isNext = true, type = PrayerType.DHUHR),
                onToggleNotification = {}
            )
            PrayerTimeItem(
                prayer = PrayerDetails("العصر", "03:45 م", type = PrayerType.ASR),
                onToggleNotification = {}
            )
        }
    }
}
