package com.der3.home.presentations.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    action: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = action,
            color = AppColors.green800
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            SectionHeader(
                title = "الأذكار",
                action = "عرض الكل"
            )
        }
    }
}
