package com.der3.muslim.main_screen.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun Der3BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onTabClick: (Screens) -> Unit,
    bottomTabs: List<BottomTab>
) {
    NavigationBar (
        modifier = modifier,
        containerColor = AppColors.green25,
    ){
        bottomTabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route::class.qualifiedName,
                onClick = { onTabClick(tab.route) },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = {
                    Text(text = stringResource(tab.label))
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.green700,
                    selectedTextColor = AppColors.green700,
                    indicatorColor = AppColors.green700.copy(alpha = 0.1f),
                    unselectedIconColor = AppColors.gray500,
                    unselectedTextColor = AppColors.gray500
                )
            )
        }
    }
}


@Preview(showBackground = true, name = "Bottom Bar Preview")
@Composable
private fun Der3BottomBarPreview() {
    // The preview now uses the actual 'bottomTabs' list from the model file.
    // This ensures the preview is always in sync with the real app.
    Der3MuslimTheme {
        Der3BottomBar(
            // To show a tab as selected, we pass its qualified class name.
            currentRoute = Der3NavigationRoute.HomeScreen::class.qualifiedName ?: "",
            onTabClick = {},
            bottomTabs = bottomTabs // Use the globally defined list
        )
    }
}
