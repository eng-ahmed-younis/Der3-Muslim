package com.der3.home.presentations.side_menu.setting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

import java.util.Locale

@Composable
fun SettingCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults
            .cardColors(containerColor = AppColors.white),
        elevation = CardDefaults
            .cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingCardPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        SettingCard {
            Text(text = "محتوى البطاقة", modifier = Modifier.padding(16.dp))
        }
    }
}
