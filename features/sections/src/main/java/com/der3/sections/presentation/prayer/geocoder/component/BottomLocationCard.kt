package com.der3.sections.presentation.prayer.geocoder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun BottomLocationCard(
    modifier: Modifier = Modifier,
    address: String,
    latitude: Double,
    longitude: Double,
    onConfirmClick: () -> Unit
) {
    val isDark = isDarkTheme
    val iconTint = if (isDark) AppColors.gold500 else AppColors.green800
    val btnBg = if (isDark) AppColors.gold500 else AppColors.green800
    val btnContent = if (isDark) Color(0xFF111827) else Color.White
    val iconBoxBg = if (isDark) AppColors.gold500.copy(alpha = 0.1f) else AppColors.green50

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Handle
            Text(
                text = stringResource(id = com.der3.ui.R.string.selected_location),
                color = AppColors.gold700, // Gold color
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                LocationOnIcon(
                    modifier = Modifier.size(56.dp),
                    iconTint = iconTint,
                    iconBoxBg = iconBoxBg
                )

                Spacer(modifier = Modifier.width(16.dp))

                LocationTextContent(
                    modifier = Modifier.weight(1f),
                    address = address,
                    latitude = latitude,
                    longitude = longitude
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = btnBg),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = btnContent)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = com.der3.ui.R.string.confirm_location),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = btnContent
                    )
                }
            }
        }

    }

    Spacer(modifier = Modifier.height(32.dp))
}



@Composable
fun LocationOnIcon(
    modifier: Modifier = Modifier,
    iconTint: Color = AppColors.green800,
    iconBoxBg: Color = AppColors.green50,
){
    Box(
        modifier = modifier
            .size(56.dp)
            .background(iconBoxBg, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )
    }
}




@Preview(name = "Light Mode", showBackground = true)
@Composable
fun BottomLocationCardLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            BottomLocationCard(
                address = "شارع إبراهيم الخليل، مكة المكرمة",
                latitude = 21.4131,
                longitude = 39.8264,
                onConfirmClick = {}
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun BottomLocationCardDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        Box(modifier = Modifier.background(AppColors.screenBackground)) {
            BottomLocationCard(
                address = "شارع إبراهيم الخليل، مكة المكرمة",
                latitude = 21.4131,
                longitude = 39.8264,
                onConfirmClick = {}
            )
        }
    }
}
