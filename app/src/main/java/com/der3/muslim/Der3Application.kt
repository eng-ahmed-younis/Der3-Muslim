package com.der3.muslim

import android.app.Application
import com.der3.muslim.fcm.MessagingServiceConfig
import com.der3.shared.service.fcm.notification.NotificationBuilder
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Der3Application : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // 2. إعداد FCM والاشتراك في المواضيع
        MessagingServiceConfig.setupFCM()
        NotificationBuilder.createNotificationChannel(this)
    }

}