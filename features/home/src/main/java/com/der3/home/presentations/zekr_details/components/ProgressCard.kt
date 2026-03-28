package com.der3.home.presentations.zekr_details.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import java.util.Locale

@Composable
fun ProgressCard(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val percentage = (progress * 100).toInt()

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 14.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Visual indicator (5 dots)

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "التقدم الإجمالي",
                    color = AppColors.gray500
                )
                Text(
                    text = "$percentage% اكتمل",
                    color = if (isDarkTheme) AppColors.green700 else AppColors.green800,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement
                    .spacedBy(6.dp)
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                if (index < percentage / 20)
                                    AppColors.green700
                                else
                                    if (isDarkTheme) AppColors.green700.copy(alpha = 0.2f) else AppColors.gray200
                            )
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun ProgressCardLightPreview() {
    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            ProgressCard(progress = 0.65f)
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProgressCardDarkPreview() {
    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            ProgressCard(progress = 0.65f)
        }
    }
}