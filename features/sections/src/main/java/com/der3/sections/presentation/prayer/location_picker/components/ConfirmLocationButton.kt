package com.der3.sections.presentation.prayer.location_picker.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.der3.model.AppStyle
import com.der3.ui.R
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.isDarkTheme

@Composable
fun ConfirmLocationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val confirmBtnBg = if (isDarkTheme) AppColors.gold500.copy(alpha = 0.15f) else Color(0xFFF5E6C4)
    val confirmBtnContent = if (isDarkTheme) AppColors.gold500 else Color(0xFF5D4037)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = confirmBtnBg),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = confirmBtnContent
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.confirm_location),
                color = confirmBtnContent,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ConfirmLocationButtonPreview() {
    Der3MuslimTheme(style = AppStyle.LIGHT) {
        ConfirmLocationButton(onClick = {})
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ConfirmLocationButtonDarkPreview() {
    Der3MuslimTheme(style = AppStyle.DARK) {
        ConfirmLocationButton(onClick = {})
    }
}
