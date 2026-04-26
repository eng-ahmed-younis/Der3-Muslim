package com.der3.sections.presentation.prayer.prayer_setting.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Box(
            modifier = Modifier
                .width(4.dp)
                .height(20.dp)
                .background(AppColors.gold500, RoundedCornerShape(2.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            color = if (isDarkTheme) AppColors.gray900Text else AppColors.green900,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun SectionHeaderLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            SectionHeader(title = "الموقع")
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SectionHeaderDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            SectionHeader(title = "الموقع")
        }
    }
}
