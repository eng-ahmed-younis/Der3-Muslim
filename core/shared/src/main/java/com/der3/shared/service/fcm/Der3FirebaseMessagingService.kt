package com.der3.shared.service.fcm

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Der3FirebaseMessagingService() : FirebaseMessagingService() {

    @Inject
    lateinit var firebaseFirestore: FirebaseFirestore



    private val CHANNEL_ID = "customer_channel"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title ?: "Notification"
        val body = remoteMessage.notification?.body ?: ""
        //showNotification(title, body)
    }



    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}