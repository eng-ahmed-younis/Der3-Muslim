package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.muslim.main_screen.drawer.model.DrawerItem
import com.der3.screens.Der3NavigationRoute
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun DrawerRow(
    item: DrawerItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background =
        if (selected) AppColors.green50 else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(50))
            .background(background)
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = if (selected) AppColors.green700 else AppColors.green500
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(item.title),
            fontSize = 16.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color(0xFF1F6B2D) else Color.DarkGray
        )
    }
}


@Preview(showBackground = true, name = "Drawer Row States")
@Composable
private fun DrawerRowPreview() {
    Der3MuslimTheme {
        Column(modifier = Modifier.padding(10.dp)) {
            // State: Selected
            DrawerRow(
                item = DrawerItem(
                    title = R.string.home_title,
                    icon = Icons.Default.Home,
                    route = Der3NavigationRoute.MainScreen
                ),
                selected = true,
                onClick = {}
            )

            // State: Unselected
            DrawerRow(
                item = DrawerItem(
                    title = R.string.settings_title,
                    icon = Icons.Default.Settings,
                    route = Der3NavigationRoute.SettingsScreen
                ),
                selected = false,
                onClick = {}
            )
        }
    }
}