package com.der3.home.presentations.notification.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = AppColors.gray500,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = AppColors.gray500.copy(alpha = 0.2f)
        )
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun SectionHeaderPreview() {
    Der3MuslimTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            SectionHeader(title = "اليوم")
        }
    }
}
