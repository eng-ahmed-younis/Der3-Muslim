package com.der3.shared.service.fcm

import android.util.Log
import com.der3.shared.service.fcm.notification.NotificationBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class Der3MuslimFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FCM_Service"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "📨 Message Received: ${remoteMessage.data}")

        // 1. التعامل مع Notification Payload (يرسلها Firebase Console)
        remoteMessage.notification?.let {
            NotificationBuilder.showNotification(
                context = this,
                title = it.title ?: "درع المسلم",
                jsonBody = it.body ?: "",
            )
        }

        // 2. التعامل مع Data Payload (يرسلها السيرفر أو API)
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: "تنبيه جديد"
            val body = remoteMessage.data["body"] ?: remoteMessage.data["message"] ?: ""
            val image = remoteMessage.data["image"]
            NotificationBuilder.showNotification(
                context = this,
                title = title,
                jsonBody = remoteMessage.data.toString()
            )
        }
    }

    /** token can change!
     * 1- App reinstall
     * 2- User clears app data
     * 3- Security updates
     * */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New Token: $token")
        // Send new token to your server
        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }
}