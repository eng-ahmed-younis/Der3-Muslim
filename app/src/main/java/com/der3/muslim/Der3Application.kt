package com.der3.muslim

import android.app.Application
import com.der3.shared.service.fcm.notification.NotificationBuilder
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Der3Application : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationBuilder.createNotificationChannel(this)
    }

}