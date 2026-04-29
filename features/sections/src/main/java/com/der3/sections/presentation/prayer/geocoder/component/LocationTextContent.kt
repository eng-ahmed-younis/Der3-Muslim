package com.der3.sections.presentation.prayer.geocoder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun LocationTextContent(
    modifier: Modifier = Modifier,
    address: String,
    latitude: Double,
    longitude: Double
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = address.ifEmpty { "جاري تحديد الموقع..." },
            color = AppColors.green900,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = "${String.format(Locale.ENGLISH, "%.4f", latitude)}° N, ${String.format(Locale.ENGLISH, "%.4f", longitude)}° E",
            color = AppColors.gray500,
            fontSize = 12.sp
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LocationTextContentLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            LocationTextContent(
                address = "شارع إبراهيم الخليل، مكة المكرمة",
                latitude = 21.4131,
                longitude = 39.8264
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun LocationTextContentDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            LocationTextContent(
                address = "شارع إبراهيم الخليل، مكة المكرمة",
                latitude = 21.4131,
                longitude = 39.8264
            )
        }
    }
}
