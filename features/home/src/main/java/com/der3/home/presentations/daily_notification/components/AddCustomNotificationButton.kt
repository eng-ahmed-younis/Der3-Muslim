package com.der3.home.presentations.daily_notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import android.content.res.Configuration
import com.der3.model.AppStyle
import com.der3.ui.themes.Der3MuslimTheme
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.isDarkTheme

@Composable
fun AddCustomNotificationButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val containerColor = if (isDarkTheme) AppColors.gold700 else AppColors.green800
    val contentColor = if (isDarkTheme) AppColors.green900 else AppColors.white

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(contentColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = containerColor
            )
        }
        Spacer(Modifier.width(10.dp))


        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun AddCustomNotificationButtonPreview() {
    Der3MuslimTheme(
        style = if (androidx.compose.foundation.isSystemInDarkTheme()) AppStyle.DARK else AppStyle.LIGHT
    ) {
        Box(
            modifier = Modifier
                .background(AppColors.screenBackground)
                .padding(16.dp)
        ) {
            AddCustomNotificationButton(
                modifier = Modifier,
                text = "إضافة تنبيه مخصص",
                onClick = {}
            )
        }
    }
}
