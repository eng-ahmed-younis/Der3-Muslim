package com.der3.home.presentations.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import android.content.res.Configuration
import com.der3.ui.themes.isDarkTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.der3.home.domain.model.NotificationItem
import com.der3.home.domain.model.NotificationType
import com.der3.ui.themes.AppColors
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    notification: NotificationItem
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isDarkTheme) {
                    Modifier.border(
                        width = 1.dp,
                        color = AppColors.green700.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AppColors.green500.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getIconForType(notification.type),
                    contentDescription = null,
                    tint = if (isDarkTheme) AppColors.gold700 else AppColors.gold700,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title and Info (Middle)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppColors.green800,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = notification.description,
                    color = AppColors.gray900Text,
                    fontSize = 13.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                notification.imageUrl?.let { url ->
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                notification.imageBitmap?.let { bitmap ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    color = AppColors.gray900Text.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End,

                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

private fun getIconForType(type: NotificationType): ImageVector {
    return when (type) {
        NotificationType.DAILY -> Icons.Default.NotificationsActive
        NotificationType.GENERAL -> Icons.Default.Notifications
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun NotificationCardLightPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.LIGHT) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                NotificationCard(
                    notification = NotificationItem(
                        id = "1",
                        title = "حان موعد صلاة العصر",
                        description = " الصلاة خير من النوم، أقم صلاتك تنعم بحياتك.",
                        time = "10:30",
                        type = NotificationType.GENERAL
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotificationCardDarkPreview() {
    Der3MuslimTheme(style = com.der3.model.AppStyle.DARK) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                NotificationCard(
                    notification = NotificationItem(
                        id = "1",
                        title = "حان موعد صلاة العصر",
                        description = " الصلاة خير من النوم، أقم صلاتك تنعم بحياتك.",
                        time = "10:30",
                        type = NotificationType.GENERAL
                    )
                )
            }
        }
    }
}
