package com.der3.sections.presentation.prayer.prayer_setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.sections.presentation.prayer.prayer_setting.mvi.PrayerType
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isDarkTheme

import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.der3.model.AppStyle
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun ManualAdjustmentSection(
    offsets: Map<PrayerType, Int>,
    onOffsetChange: (PrayerType, Int) -> Unit
) {
    val sectionBg = if (isDarkTheme) AppColors.green100 else AppColors.green800
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = sectionBg)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.Restore,
                    contentDescription = null,
                    tint = AppColors.gold500,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = stringResource(id = com.der3.ui.R.string.manual_adjustment_label),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(id = com.der3.ui.R.string.manual_adjustment_desc),
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            val prayerPairs = PrayerType.entries.filter { it != PrayerType.ISHA }.chunked(2)
            
            prayerPairs.forEach { pair ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    pair.forEach { prayer ->
                        AdjustmentItem(
                            prayer = prayer,
                            offset = offsets[prayer] ?: 0,
                            onOffsetChange = { onOffsetChange(prayer, it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Isha at the bottom
            AdjustmentItem(
                prayer = PrayerType.ISHA,
                offset = offsets[PrayerType.ISHA] ?: 0,
                onOffsetChange = { onOffsetChange(PrayerType.ISHA, it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun ManualAdjustmentSectionLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            ManualAdjustmentSection(offsets = PrayerType.entries.associateWith { 0 }, onOffsetChange = { _, _ -> })
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ManualAdjustmentSectionDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            ManualAdjustmentSection(offsets = PrayerType.entries.associateWith { 0 }, onOffsetChange = { _, _ -> })
        }
    }
}

@Composable
fun AdjustmentItem(prayer: PrayerType, offset: Int, onOffsetChange: (Int) -> Unit, modifier: Modifier) {
    val prayerName = when(prayer) {
        PrayerType.FAJR -> stringResource(id = com.der3.ui.R.string.prayer_fajr)
        PrayerType.DHUHR -> stringResource(id = com.der3.ui.R.string.prayer_dhuhr)
        PrayerType.ASR -> stringResource(id = com.der3.ui.R.string.prayer_asr)
        PrayerType.MAGHRIB -> stringResource(id = com.der3.ui.R.string.prayer_maghrib)
        PrayerType.ISHA -> stringResource(id = com.der3.ui.R.string.prayer_isha)
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = prayerName, color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .clickable { onOffsetChange(offset - 1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "-", color = Color.White, fontWeight = FontWeight.Bold)
                }
                
                Text(
                    text = if (offset >= 0) "+$offset" else "$offset",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(AppColors.gold500, CircleShape)
                        .clickable { onOffsetChange(offset + 1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = if (isDarkTheme) AppColors.green50 else AppColors.green900,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
