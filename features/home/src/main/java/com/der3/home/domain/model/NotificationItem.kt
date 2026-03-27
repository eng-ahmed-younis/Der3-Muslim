package com.der3.home.domain.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.vector.ImageVector

data class NotificationItem(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val icon: ImageVector? = null,
    val iconBackgroundColor: Long? = null,
    val imageUrl: String? = null,
    val imageBitmap: Bitmap? = null,
    val type: NotificationType = NotificationType.GENERAL
)

enum class NotificationType {
    GENERAL, DAILY
}
