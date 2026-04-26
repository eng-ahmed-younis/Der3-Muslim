package com.der3.sections.presentation.prayer.prayer_setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isDarkTheme

import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.der3.model.AppStyle
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun HighLatitudeSection(selectedMethod: String, onMethodSelect: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = AppColors.gold500,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = com.der3.ui.R.string.high_latitude_adjustment_label),
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) AppColors.gray900Text else AppColors.green900
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.cardColor)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDarkTheme) AppColors.gray100 else AppColors.gray50)
                    .padding(12.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                val displayMethod = if (selectedMethod == "Angle Based") {
                    stringResource(id = com.der3.ui.R.string.angle_based)
                } else {
                    selectedMethod
                }
                Text(
                    text = displayMethod,
                    color = AppColors.gray500
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun HighLatitudeSectionLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            HighLatitudeSection(selectedMethod = "Angle Based", onMethodSelect = {})
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HighLatitudeSectionDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            HighLatitudeSection(selectedMethod = "Angle Based", onMethodSelect = {})
        }
    }
}
