package com.der3.sections.presentation.prayer.location_picker.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isDarkTheme

@Composable
fun ManualMapPickerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val manualBtnBg = if (isDarkTheme) AppColors.gold500.copy(alpha = 0.15f) else Color(0xFFF5E6C4)
    val manualBtnContent = if (isDarkTheme) AppColors.gold500 else Color(0xFF5D4037)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = manualBtnBg),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = manualBtnContent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.manual_selection_on_map),
                color = manualBtnContent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
