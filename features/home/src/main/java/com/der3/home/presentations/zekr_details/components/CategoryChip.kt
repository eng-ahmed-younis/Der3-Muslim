package com.der3.home.presentations.zekr_details.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun CategoryChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isDarkTheme) AppColors.cardColor else AppColors.green100)
            .border(
                width = 1.dp,
                color = if (isDarkTheme) AppColors.green700.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isDarkTheme) AppColors.green700 else AppColors.green800,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun CategoryChipLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CategoryChip(text = "الَّذِينَ آمَنُوا وَتَطْمَئِنُّ قُلُوبُهُم بِذِكْرِ اللَّهِ ۗ أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ")
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CategoryChipDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CategoryChip(text = "الَّذِينَ آمَنُوا وَتَطْمَئِنُّ قُلُوبُهُم بِذِكْرِ اللَّهِ ۗ أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ")
        }
    }
}