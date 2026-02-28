package com.der3.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme


@Composable
fun SectionLabel(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        color = AppColors.green800,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = modifier
    )
}


@Preview(showBackground = true, name = "Section Label Preview")
@Composable
private fun SectionLabelPreview() {
    Der3MuslimTheme {
        // Adding padding to the preview so it's not stuck to the edges
        Box(modifier = Modifier.padding(16.dp)) {
            SectionLabel(text = "اسم التنبيه")
        }
    }
}