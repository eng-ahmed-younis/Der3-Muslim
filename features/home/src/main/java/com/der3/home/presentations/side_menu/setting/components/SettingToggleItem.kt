package com.der3.home.presentations.side_menu.setting.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

import java.util.Locale

@Composable
fun SettingToggleItem(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isDarkTheme) AppColors.green500 else AppColors.green800,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(4f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.gray900Text
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.gray500
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.8f),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = if (isDarkTheme) AppColors.green500 else AppColors.green800,
                uncheckedThumbColor = if (isDarkTheme) AppColors.gray400 else Color.White,
                uncheckedTrackColor = if (isDarkTheme) AppColors.gray300 else AppColors.gray200,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun SettingToggleItemLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = AppColors.cardColor) {
            SettingToggleItem(
                title = "الوضع الليلي",
                description = "تبديل بين المظهر الفاتح والداكن",
                icon = Icons.Default.DarkMode,
                checked = true,
                onCheckedChange = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SettingToggleItemDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Surface(color = AppColors.cardColor) {
            SettingToggleItem(
                title = "الوضع الليلي",
                description = "تبديل بين المظهر الفاتح والداكن",
                icon = Icons.Default.DarkMode,
                checked = true,
                onCheckedChange = {}
            )
        }
    }
}
