package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.muslim.main_screen.drawer.model.DrawerItem
import com.der3.muslim.main_screen.drawer.model.drawerItems
import com.der3.muslim.utils.getAppVersionName
import com.der3.screens.Screens
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale


@Composable
fun AzkarDrawer(
    currentRoute: String,
    onItemClick: (Screens) -> Unit,
    items: List<DrawerItem>
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val drawerWidth = (configuration.screenWidthDp * 0.8).dp

    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(drawerWidth),
        drawerContainerColor = AppColors.screenBackground,
            drawerShape = androidx.compose.foundation.shape.RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 24.dp,
                bottomEnd = 24.dp
            )
        ) {
            DrawerHeader()

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                items.forEach { item ->
                    DrawerRow(
                        item = item,
                        selected = currentRoute == item.route::class.qualifiedName,
                        onClick = { onItemClick(item.route) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            DrawerFooter(version = context.getAppVersionName() ?: "unknown")
        }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AzkarDrawerPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AzkarDrawer(
            currentRoute = "home",
            onItemClick = {},
            items = drawerItems
        )
    }

}