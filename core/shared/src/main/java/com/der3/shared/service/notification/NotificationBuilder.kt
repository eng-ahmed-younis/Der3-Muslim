package com.der3.shared.service.notification

import android.os.Build

class NotificationBuilder  {

    companion object {
        private const val CHANNEL_ID = "customer_notification_channel"
    }


   /* private fun createChannelId(){
        // For Android O and above, you need to create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = "Customer Notifications"
            val descriptionText = "Notifications for customer updates"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager: android.app.NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }*/
}