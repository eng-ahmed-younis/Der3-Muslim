package com.der3.home.presentations.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.der3.ui.R
import com.der3.ui.themes.AppColors

@Composable
fun EmptyNotificationsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    color = AppColors.gold700.copy(alpha = 0.05f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.NotificationsOff,
                contentDescription = null,
                tint = AppColors.gold700,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.notification_empty_state),
            color = AppColors.gold700,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true, locale = "ar")
@Preview(showBackground = true, locale = "ar", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmptyNotificationsStatePreview() {
    Der3MuslimTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            EmptyNotificationsState()
        }
    }
}
