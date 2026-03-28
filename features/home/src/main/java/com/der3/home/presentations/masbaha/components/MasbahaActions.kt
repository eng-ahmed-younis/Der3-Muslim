package com.der3.home.presentations.masbaha.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors

@Composable
fun MasbahaActionButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .border(1.dp, AppColors.gray100, RoundedCornerShape(28.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.white,
            contentColor = AppColors.green800
        ),
        shape = RoundedCornerShape(28.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = AppColors.gold600)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MasbahaToggleActionButton(
    text: String,
    icon: ImageVector,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit
) {
    Button(
        onClick = { onToggle(!enabled) },
        modifier = modifier
            .height(56.dp)
            .border(1.dp, AppColors.gray100, RoundedCornerShape(28.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.white,
            contentColor = if (enabled) AppColors.green800 else AppColors.gray500
        ),
        shape = RoundedCornerShape(28.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) AppColors.green800 else AppColors.gray500
            )
            Text(text = text, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MasbahaActionsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MasbahaActionButton(
                text = "تصفير",
                icon = Icons.Default.Refresh,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
            MasbahaActionButton(
                text = "تعديل الهدف",
                icon = Icons.Default.Settings,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MasbahaToggleActionButton(
                text = "تنبيه صوتي",
                icon = Icons.AutoMirrored.Filled.VolumeUp,
                enabled = true,
                modifier = Modifier.weight(1f),
                onToggle = {}
            )
            MasbahaToggleActionButton(
                text = "الاهتزاز",
                icon = Icons.Default.Vibration,
                enabled = false,
                modifier = Modifier.weight(1f),
                onToggle = {}
            )
        }
    }
}
