package com.der3.muslim.main_screen.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun DrawerFooter(version: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFEDEDED))
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF1F6B2D))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.Share, contentDescription = null, tint = Color(0xFF1F6B2D))
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("الإصدار", fontSize = 14.sp, color = Color.Gray)
                Text(version, fontSize = 16.sp, color = Color(0xFF1F6B2D), fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true, name = "Drawer Footer Preview")
@Composable
private fun DrawerFooterPreview() {
    Der3MuslimTheme {
        DrawerFooter(version = "1.0.0 (24)")
    }
}