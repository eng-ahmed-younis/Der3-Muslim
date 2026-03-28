package com.der3.home.presentations.home_screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun DailyZekrCard(
    modifier: Modifier = Modifier,
    title: String = "لا إله إلا الله وحده لا شريك له",
    description: String = "له الملك وله الحمد وهو على كل شيء قدير",
    containerColor: Color? = null
) {
    val finalContainerColor = containerColor ?: AppColors.green800

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = finalContainerColor
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            Text(
                text = "ذكر اليوم",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = description,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Daily Zekr Card Green Light")
@Composable
fun DailyZekrCardPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            DailyZekrCard()
        }
    }
}

@Preview(
    showBackground = true,
    name = "Daily Zekr Card Green Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DailyZekrCardDarkPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            DailyZekrCard()
        }
    }
}

@Preview(showBackground = true, name = "Daily Zekr Card Gold Light")
@Composable
fun DailyZekrCardGoldPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            DailyZekrCard(
                containerColor = AppColors.gold600,
                title = "سبحان الله وبحمده",
                description = "مئة مرة حطت خطاياه وإن كانت مثل زبد البحر"
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Daily Zekr Card Gold Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DailyZekrCardGoldDarkPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            DailyZekrCard(
                containerColor = AppColors.gold600,
                title = "سبحان الله وبحمده",
                description = "مئة مرة حطت خطاياه وإن كانت مثل زبد البحر"
            )
        }
    }
}
