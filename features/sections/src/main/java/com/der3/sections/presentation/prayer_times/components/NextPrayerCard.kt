package com.der3.sections.presentation.prayer_times.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.sections.presentation.prayer_times.mvi.PrayerDetails
import com.der3.sections.presentation.prayer_times.mvi.PrayerType
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun NextPrayerCard(prayer: PrayerDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.green800),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "الصلاة القادمة",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                "صلاة ${prayer.name}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Text(
                prayer.time,
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
                    TimeUnit(times[0], "ساعة")
                    Text(":", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(bottom = 20.dp))
                    TimeUnit(times[1], "دقيقة")
                    Text(":", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(bottom = 20.dp))
                    TimeUnit(times[2], "ثانية")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                "متبقي على رفع الأذان",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun TimeUnit(value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(unit, fontSize = 10.sp, color = Color.White.copy(alpha = 0.7f))
    }
}

@Preview(showBackground = true)
@Composable
fun NextPrayerCardPreview() {
    Der3MuslimTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            NextPrayerCard(
                prayer = PrayerDetails("العصر", "03:45 م", isNext = true, type = PrayerType.ASR)
            )
        }
    }
}
