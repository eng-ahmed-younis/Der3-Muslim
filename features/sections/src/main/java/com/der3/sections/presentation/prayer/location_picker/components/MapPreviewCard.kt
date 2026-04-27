package com.der3.sections.presentation.prayer.location_picker.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.model.GeographyMapStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme
import com.der3.utils.AppConfig
import java.util.Locale

@Composable
fun MapPreviewCard(
    modifier: Modifier = Modifier,
    lat: Double,
    lng: Double,
    locationName: String? = null,
    mapStyle: GeographyMapStyle = GeographyMapStyle.OsmBright
) {
    val isDark = isDarkTheme
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(if (isDark) AppColors.gray100 else Color(0xFFE8F5E9)) // Map placeholder color
            ) {
                GeoapifyMapView(
                    modifier = Modifier.fillMaxSize(),
                    latitude = lat,
                    longitude = lng,
                    mapStyle = mapStyle,
                    geoapifyApiKey = AppConfig.GEOAPIFY_API_KEY,
                    context = context
                )

                // Location name tag
                if (!locationName.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(
                                color = if (isDark) AppColors.gray500.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = locationName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.gray900Text
                        )
                    }
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.latitude_label),
                        fontSize = 12.sp,
                        color = AppColors.gray400
                    )
                    Text(
                        text = "N ${String.format(Locale.ENGLISH, "%.4f", lat)}°",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) AppColors.gold500 else AppColors.green800
                    )
                }
                
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 16.dp),
                    color = AppColors.gray200
                )
                
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.longitude_label),
                        fontSize = 12.sp,
                        color = AppColors.gray400
                    )
                    Text(
                        text = "E ${String.format(Locale.ENGLISH, "%.4f", lng)}°",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) AppColors.gold500 else AppColors.green800
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun MapPreviewCardPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            MapPreviewCard(lat = 24.7136, lng = 46.6753)
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MapPreviewCardDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            MapPreviewCard(lat = 24.7136, lng = 46.6753)
        }
    }
}
