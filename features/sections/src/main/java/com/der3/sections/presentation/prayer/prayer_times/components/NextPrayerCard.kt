package com.der3.sections.presentation.prayer.prayer_times.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.sections.domain.model.PrayerDetails
import com.der3.sections.domain.model.PrayerType
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun NextPrayerCard(
    modifier: Modifier = Modifier,
    prayer: PrayerDetails
) {
    val isDark = isDarkTheme
    val containerColor = if (isDark) AppColors.cardColor else AppColors.green800
    val contentColor = Color.White
    val subContentColor = Color.White.copy(alpha = 0.7f)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isDark) {
                    Modifier.border(
                        width = 1.dp,
                        color = AppColors.gold700.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(32.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isDark) 0.dp else 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = contentColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "الصلاة القادمة",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = contentColor,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "صلاة ${prayer.name}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            
            Text(
                text = prayer.time,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.gold400
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val times = prayer.remainingTime.split(":")
                if (times.size == 3) {
                    TimeUnit(times[0], "ساعة", contentColor, subContentColor)
                    Text(":", color = contentColor, fontSize = 24.sp, modifier = Modifier.padding(bottom = 20.dp))
                    TimeUnit(times[1], "دقيقة", contentColor, subContentColor)
                    Text(":", color = contentColor, fontSize = 24.sp, modifier = Modifier.padding(bottom = 20.dp))
                    TimeUnit(times[2], "ثانية", contentColor, subContentColor)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "متبقي على رفع الأذان",
                style = MaterialTheme.typography.labelSmall,
                color = subContentColor
            )
        }
    }
}

@Composable
fun TimeUnit(value: String, unit: String, valueColor: Color, unitColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = valueColor)
        Text(unit, fontSize = 10.sp, color = unitColor)
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun NextPrayerCardPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            NextPrayerCard(
                prayer = PrayerDetails("العصر", "03:45 م", isNext = true, type = PrayerType.ASR)
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
fun NextPrayerCardDarkPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.DARK) {
        Box(modifier = Modifier.padding(16.dp)) {
            NextPrayerCard(
                prayer = PrayerDetails("العصر", "03:45 م", isNext = true, type = PrayerType.ASR)
            )
        }
    }
}
