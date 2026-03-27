package com.der3.shared.service.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.*

class Der3BootReceiver : BroadcastReceiver() {

    private val TAG = "BootReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d(TAG, "Device boot completed - re-initializing FCM")

            // Resubscribe to topics after boot
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Get current token and resubscribe
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val token = task.result
                            Log.d(TAG, "Boot token: $token")

                            // Resubscribe to topics
                            FirebaseMessaging.getInstance()
                                .subscribeToTopic("all")
                                .addOnCompleteListener { subscribeTask ->
                                    if (subscribeTask.isSuccessful) {
                                        Log.d(TAG, "Resubscribed to topics after boot")
                                    }
                                }
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error during boot re-initialization: ${e.message}")
                }
            }
        }
    }
}