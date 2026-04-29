package com.der3.sections.presentation.prayer.prayer_setting.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import java.util.Locale

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    locationName: String,
    onChangeClick: () -> Unit
) {
    val isDark = isDarkTheme
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        if (isDark) AppColors.gold500.copy(alpha = 0.1f) else AppColors.green25,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = if (isDark) AppColors.gold500 else AppColors.green800
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.
                        basicMarquee(),
                    text = locationName.ifEmpty { stringResource(id = com.der3.ui.R.string.location_default_riyadh) },
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    color = AppColors.gray900Text
                )
                Text(
                    text = stringResource(id = com.der3.ui.R.string.gps_auto_detect),
                    fontSize = 12.sp,
                    color = AppColors.gray500,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(id = com.der3.ui.R.string.change),
                color = AppColors.gold500,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onChangeClick() }
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun LocationCardLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            LocationCard(locationName = "الرياض، المملكة العربية السعودية", onChangeClick = {})
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LocationCardDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK, language = Locale.Builder().setLanguage("ar").build()) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            LocationCard(locationName = "الرياض، المملكة العربية السعودية", onChangeClick = {})
        }
    }
}
