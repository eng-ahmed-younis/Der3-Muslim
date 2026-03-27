package com.der3.sections.presentation.prayer.prayer_times.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.sections.domain.model.PrayerDetails
import com.der3.sections.domain.model.PrayerType
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun PrayerTimeItem(
    prayer: PrayerDetails,
    onToggleNotification: () -> Unit
) {
    val backgroundColor = if (prayer.isNext) AppColors.green50 else Color.White
    val borderColor = if (prayer.isNext) AppColors.green400 else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Right side in RTL (Start)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = getPrayerIcon(prayer.type)),
                    contentDescription = null,
                    tint = if (prayer.isNext) AppColors.green800 else if (prayer.isPassed) AppColors.gray500 else AppColors.green700,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        prayer.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (prayer.isNext) AppColors.green800 else if (prayer.isPassed) AppColors.gray500 else Color.Black
                    )
                    Text(
                        prayer.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (prayer.isNext) AppColors.green800.copy(alpha = 0.6f) else AppColors.blueGray400
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
                    color = if (prayer.isNext) AppColors.green800 else if (prayer.isPassed) AppColors.gray500 else AppColors.green900
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
                            prayer.isNext -> AppColors.green800
                            prayer.notificationEnabled -> AppColors.green400
                            else -> AppColors.gray400
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

fun getPrayerIcon(type: PrayerType): Int {
    return when (type) {
        PrayerType.FAJR -> R.drawable.ic_fajr
        PrayerType.SUNRISE -> R.drawable.ic_sunrise
        PrayerType.DHUHR -> R.drawable.ic_dhuhr
        PrayerType.ASR -> R.drawable.ic_asr
        PrayerType.MAGHRIB -> R.drawable.ic_maghrib
        PrayerType.ISHA -> R.drawable.ic_isha
        PrayerType.IMSAK -> R.drawable.ic_fajr // Fallback
        PrayerType.MIDNIGHT -> R.drawable.ic_isha // Fallback
        PrayerType.FIRST_THIRD -> R.drawable.ic_isha // Fallback
        PrayerType.LAST_THIRD -> R.drawable.ic_isha // Fallback
    }
}

@Preview(showBackground = true)
@Composable
fun PrayerTimeItemPreview() {
    Der3MuslimTheme(language = Locale.Builder().setLanguage("ar").build()) {
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
