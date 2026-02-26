package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.der3.muslim.main_screen.drawer.model.DrawerItem
import com.der3.muslim.main_screen.drawer.model.drawerItems
import com.der3.screens.Screens
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun AzkarDrawer(
    currentRoute: String,
    onItemClick: (Screens) -> Unit,
    items: List<DrawerItem>
) {

    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp),
        drawerContainerColor = Color(0xFFF3F4F6)
    ) {

        DrawerHeader()

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            DrawerRow(
                item = item,
                // item.route::class.qualifiedName will return a full string path like: "com.der3.screens.Screens.MainScreen"
                selected = currentRoute == item.route::class.qualifiedName,
                onClick = { onItemClick(item.route) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        DrawerFooter(version = "2.4.0")
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AzkarDrawerPreview() {
    Der3MuslimTheme {
        AzkarDrawer(
            currentRoute = "home",
            onItemClick = {},
            items = drawerItems
        )
    }

}