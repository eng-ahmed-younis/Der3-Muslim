package com.der3.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun Der3TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    titleColor: Color = AppColors.gray900Text,
    navigationIconColor: Color = AppColors.gray900Text,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    trailingContent: @Composable (() -> Unit)? = null
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔹 Back Button
        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = navigationIconColor
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        // 🔹 Title
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }

        // 🔹 Trailing Content

        trailingContent?.invoke()

    }
}

@Preview(showBackground = true)
@Composable
fun Der3TopAppBarPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        Der3TopAppBar(
            title = "Der3 Top App Bar",
            backgroundColor = AppColors.gray50,
            onBackClick = {},
            trailingContent = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = AppColors.gray900Text
                    )
                }
            }
        )
    }
}
