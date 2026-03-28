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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import android.content.res.Configuration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.der3.ui.R

@Composable
fun Der3BottomBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    onTabClick: (Screens) -> Unit,
    bottomTabs: List<BottomTab>
) {
    NavigationBar (
        modifier = modifier,
        containerColor = AppColors.white,
        tonalElevation = 8.dp
    ){
        bottomTabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route::class.qualifiedName,
                onClick = { onTabClick(tab.route) },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = {
                    Text(
                        text = stringResource(tab.label),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.cairo_medium))
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.green800,
                    selectedTextColor = AppColors.green800,
                    indicatorColor = AppColors.green800.copy(alpha = 0.12f),
                    unselectedIconColor = AppColors.gray500,
                    unselectedTextColor = AppColors.gray500
                )
            )
        }
    }
}


@Preview(showBackground = true, name = "Bottom Bar Light")
@Composable
private fun Der3BottomBarPreview() {
    Der3MuslimTheme {
        Der3BottomBar(
            currentRoute = Der3NavigationRoute.HomeScreen::class.qualifiedName ?: "",
            onTabClick = {},
            bottomTabs = bottomTabs
        )
    }
}

@Preview(
    showBackground = true,
    name = "Bottom Bar Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun Der3BottomBarDarkPreview() {
    Der3MuslimTheme {
        Der3BottomBar(
            currentRoute = Der3NavigationRoute.HomeScreen::class.qualifiedName ?: "",
            onTabClick = {},
            bottomTabs = bottomTabs
        )
    }
}
