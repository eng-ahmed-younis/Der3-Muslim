package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

@Composable
fun ReadingInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    countText: String,
    durationText: String,
    icon: ImageVector = Icons.AutoMirrored.Filled.MenuBook
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults
            .cardColors(
                containerColor = if (isDarkTheme) AppColors.dailyCardColor.copy(alpha = 0.6f)
                else AppColors.green800
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.gold500,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.cairo_medium)),
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = description,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SmallInfoChip(
                    text = countText,
                    icon = Icons.Default.AutoStories
                )
                SmallInfoChip(
                    text = durationText,
                    icon = Icons.Filled.Alarm
                )
            }
        }
    }
}

@Composable
fun SmallInfoChip(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector
) {
    Box(
        modifier = modifier
            .background(
                color = AppColors.white.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
            )
            Text(
                text = text,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun ReadingInfoCardLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier
            .background(AppColors.screenBackground)
            .padding(16.dp)) {
            ReadingInfoCard(
                title = "وقت القراءة",
                description = "من طلوع الفجر إلى طلوع الشمس",
                countText = "٢٤ ذكر",
                durationText = "١٠ دقائق"
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
private fun ReadingInfoCardDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier
            .background(AppColors.screenBackground)
            .padding(16.dp)) {
            ReadingInfoCard(
                title = "وقت القراءة",
                description = "من طلوع الفجر إلى طلوع الشمس",
                countText = "٢٤ ذكر",
                durationText = "١٠ دقائق"
            )
        }
    }
}