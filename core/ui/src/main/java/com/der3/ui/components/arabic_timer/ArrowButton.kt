package com.der3.ui.components.arabic_timer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.Der3MuslimTheme
import com.example.timepicker.SideValueColor

@Composable
fun ArrowButton(
    modifier: Modifier = Modifier,
    up: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
    ) {
        Text(
            text = if (up) "▲" else "▼",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SideValueColor
        )
    }
}

@Preview(showBackground = true, name = "Arrow Buttons Preview")
@Composable
private fun ArrowButtonPreview() {
    Der3MuslimTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            ArrowButton(up = true, onClick = {})
            Spacer(modifier = Modifier.width(16.dp))
            ArrowButton(up = false, onClick = {})
        }
    }
}