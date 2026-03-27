package com.der3.shared.service.fcm.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object NotificationPermission {

    /**
     * Checks the current status of the notification permission.
     */
    fun checkNotificationPermission(context: Context): PermissionStatus {
        // Before Android 13 (Tiramisu), notifications are granted by default on install
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return PermissionStatus.GRANTED
        }

        return when (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        )) {
            PackageManager.PERMISSION_GRANTED -> PermissionStatus.GRANTED
            PackageManager.PERMISSION_DENIED -> {
                // You can add logic here to check if it's the first time or permanently denied
                PermissionStatus.DENIED
            }
            else -> PermissionStatus.NOT_DETERMINED
        }
    }



    enum class PermissionStatus {
        GRANTED,
        DENIED,
        NOT_DETERMINED
    }
}