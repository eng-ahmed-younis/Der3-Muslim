package com.der3.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.der3.ui.themes.AppColors

import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onShareAsText: () -> Unit,
    onShareAsImage: () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = AppColors.white,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            ShareBottomSheetContent(
                onShareAsText = onShareAsText,
                onShareAsImage = onShareAsImage
            )
        }
    }
}

@Composable
fun ShareBottomSheetContent(
    onShareAsText: () -> Unit,
    onShareAsImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        ShareOptionItem(
            title = "مشاركة كنص",
            icon = Icons.Default.TextFields,
            onClick = onShareAsText
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = AppColors.gray50)

        ShareOptionItem(
            title = "مشاركة كصورة",
            icon = Icons.Default.Image,
            onClick = onShareAsImage
        )
    }
}

@Composable
private fun ShareOptionItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppColors.green700
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = AppColors.gray900Text
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShareBottomSheetPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        ShareBottomSheet(
            isVisible = true,
            onDismiss = {},
            onShareAsText = {},
            onShareAsImage = {}
        )
    }
}
