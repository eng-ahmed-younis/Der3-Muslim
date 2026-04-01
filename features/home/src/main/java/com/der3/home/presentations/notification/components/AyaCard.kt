package com.der3.home.presentations.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.der3.ui.themes.isDarkTheme
import androidx.compose.foundation.border
import com.der3.ui.themes.Der3MuslimTheme
import androidx.compose.ui.res.stringResource
import com.der3.ui.R
import com.der3.ui.themes.AppColors

@Composable
fun AyaCard(
    modifier: Modifier = Modifier,
    aya: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isDarkTheme) {
                    Modifier.border(
                        width = 1.dp,
                        color = AppColors.gold700.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) AppColors.cardColor else AppColors.green800
        )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(AppColors.gold700),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.notification_aya_of_the_day),
                color = if (isDarkTheme) AppColors.gray900Text.copy(alpha = 0.6f) else AppColors.white.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = aya,
                color = if (isDarkTheme) AppColors.gray900Text else AppColors.white,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.width(60.dp), color = AppColors.gold700, thickness = 3.dp)
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AyaCardLightPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                AyaCard(aya = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ")
            }
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AyaCardDarkPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                AyaCard(aya = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ")
            }
        }
    }
}
