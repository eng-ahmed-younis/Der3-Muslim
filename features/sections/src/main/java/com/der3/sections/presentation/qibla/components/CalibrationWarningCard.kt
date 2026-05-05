package com.der3.sections.presentation.qibla.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme

@Composable
fun CalibrationWarningCard(modifier: Modifier = Modifier) {
    val isDark = com.der3.ui.themes.isDarkTheme
    val gold = AppColors.gold700
    
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) gold.copy(alpha = 0.1f) else Color(0xFFFDF6E3)
        ),
        border = BorderStroke(
            1.dp,
            if (isDark) gold.copy(alpha = 0.2f) else Color(0xFFF9EBC8)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = com.der3.ui.R.string.qibla_calibration_hint),
                style = MaterialTheme.typography.bodySmall,
                color = if (isDark) gold else Color(0xFFB8942E),
                lineHeight = 18.sp,
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = if (isDark) gold else Color(0xFFD4A017),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun CalibrationWarningCardPreviewLight() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        CalibrationWarningCard(modifier = Modifier.padding(16.dp))
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CalibrationWarningCardPreviewDark() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        CalibrationWarningCard(modifier = Modifier.padding(16.dp))
    }
}
