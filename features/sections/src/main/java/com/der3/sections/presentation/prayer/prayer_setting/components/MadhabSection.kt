package com.der3.sections.presentation.prayer.prayer_setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.der3.sections.presentation.prayer.prayer_setting.mvi.Madhab
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isDarkTheme

import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.der3.model.AppStyle
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun MadhabSection(selectedMadhab: Madhab, onMadhabSelect: (Madhab) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = null,
                tint = if (isDarkTheme) AppColors.gold500 else AppColors.green800,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = com.der3.ui.R.string.madhab_label),
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
            Row(modifier = Modifier.padding(4.dp)) {
                MadhabToggleItem(
                    text = stringResource(id = com.der3.ui.R.string.shafi_majority),
                    isSelected = selectedMadhab == Madhab.SHAFI,
                    onClick = { onMadhabSelect(Madhab.SHAFI) },
                    modifier = Modifier.weight(1f)
                )
                MadhabToggleItem(
                    text = stringResource(id = com.der3.ui.R.string.hanafi),
                    isSelected = selectedMadhab == Madhab.HANAFI,
                    onClick = { onMadhabSelect(Madhab.HANAFI) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun MadhabSectionLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            MadhabSection(selectedMadhab = Madhab.SHAFI, onMadhabSelect = {})
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MadhabSectionDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            MadhabSection(selectedMadhab = Madhab.HANAFI, onMadhabSelect = {})
        }
    }
}

@Composable
fun MadhabToggleItem(text: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    val isDark = isDarkTheme
    val selectedBg = if (isDark) AppColors.gold500 else AppColors.green800
    val selectedText = if (isDark) AppColors.green50 else Color.White

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) selectedBg else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) selectedText else AppColors.gray500,
            fontWeight = FontWeight.Bold
        )
    }
}
