package com.der3.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.der3.model.AppStyle
import com.der3.ui.models.MenuItemData
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun CustomMenu(
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    onDismiss: () -> Unit = {},
    menuItems: List<MenuItemData>,
    onMenuItemClicked: (MenuItemData) -> Unit = {}
) {
    val shape = RoundedCornerShape(24.dp)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        DropdownMenu(
            modifier = modifier
                .clip(shape),
            expanded = isVisible,
            onDismissRequest = onDismiss,
            containerColor = AppColors.zekrPanelBg,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            // No Surface needed if everything is transparent
            Column(
                modifier = Modifier.clip(shape)
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = item.text,
                                color = AppColors.gray900Text
                            ) 
                        },
                        onClick = {
                            onDismiss()
                            onMenuItemClicked(item)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.text,
                                tint = AppColors.zekrIconTint
                            )
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun CustomMenuPreview() {
    // We use a state to control visibility in the preview if needed
    val menuItems = listOf(
        MenuItemData(text = "Share", icon = Icons.Default.Share),
        MenuItemData(text = "Favorite", icon = Icons.Default.Favorite),
        MenuItemData(text = "Settings", icon = Icons.Default.Settings)
    )

    Der3MuslimTheme(
        style = AppStyle.LIGHT,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            CustomMenu(
                isVisible = true, // Set to true to see it in the Preview pane
                onDismiss = {},
                menuItems = menuItems,
                onMenuItemClicked = {}
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
fun CustomMenuDarkPreview() {
    val menuItems = listOf(
        MenuItemData(text = "Share", icon = Icons.Default.Share),
        MenuItemData(text = "Favorite", icon = Icons.Default.Favorite),
        MenuItemData(text = "Settings", icon = Icons.Default.Settings)
    )

    Der3MuslimTheme(
        style = AppStyle.DARK,
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            CustomMenu(
                isVisible = true,
                onDismiss = {},
                menuItems = menuItems,
                onMenuItemClicked = {}
            )
        }
    }
}