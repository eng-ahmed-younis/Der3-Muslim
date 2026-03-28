package com.der3.home.presentations.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun HomeTopHeader(
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.screenBackground,
    onDrawerClick: () -> Unit,
    onNotificationClick: () -> Unit
) {


    val (iconBg, iconColor) = if (isDarkTheme) {
        AppColors.gray900Text.copy(alpha = 0.15f) to AppColors.gray900Text
    } else {
        AppColors.green50 to AppColors.green800
    }



    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔹 Drawer Icon
        IconButton(onClick = onDrawerClick) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .background(iconBg)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer",
                    tint = iconColor
                )
            }
        }


        // 🔹 Title
        Text(
            text = stringResource(id = R.string.azkar_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.gray900Text
        )

        // 🔹 Notification Icon
        IconButton(onClick = onNotificationClick) {
            Box (
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconBg
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = iconColor
                )

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun HomeTopHeaderLightPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        HomeTopHeader(
            onDrawerClick = {},
            backgroundColor = AppColors.screenBackground,
            onNotificationClick = {}
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun HomeTopHeaderDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        HomeTopHeader(
            onDrawerClick = {},
            backgroundColor = AppColors.screenBackground,
            onNotificationClick = {}
        )
    }
}
