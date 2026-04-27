package com.der3.sections.presentation.prayer.location_picker.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.sections.presentation.prayer.location_picker.mvi.CityUi
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun CityCard(
    modifier: Modifier = Modifier,
    city: CityUi,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = city.country, fontSize = 10.sp, color = AppColors.gray400)
            Text(text = city.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AppColors.gray900Text)
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun CityCardPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CityCard(
                city = CityUi("الرياض", "المملكة العربية السعودية", 24.7136, 46.6753),
                onClick = {}
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
private fun CityCardDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground).padding(16.dp)) {
            CityCard(
                city = CityUi("الرياض", "المملكة العربية السعودية", 24.7136, 46.6753),
                onClick = {}
            )
        }
    }
}
