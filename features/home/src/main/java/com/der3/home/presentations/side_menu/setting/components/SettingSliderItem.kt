package com.der3.home.presentations.side_menu.setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun SettingSliderItem(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    icon: ImageVector
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.green800,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.gray900Text
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "A", color = AppColors.gray400, fontSize = 12.sp)
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 12f..30f,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = AppColors.green800,
                    activeTrackColor = AppColors.green800,
                    inactiveTrackColor = AppColors.gray200
                )
            )
            Text(text = "A", color = AppColors.gray500, fontSize = 20.sp)
        }
        Text(
            text = "${value.toInt()}px",
            modifier = Modifier.background(AppColors.green50, RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 4.dp),
            color = AppColors.green800,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSliderItemPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        SettingSliderItem(
            title = "حجم الخط",
            value = 18f,
            onValueChange = {},
            icon = Icons.Default.TextFields
        )
    }
}
