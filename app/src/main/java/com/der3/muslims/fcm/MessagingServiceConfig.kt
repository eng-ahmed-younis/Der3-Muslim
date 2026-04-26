package com.der3.muslims.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

object MessagingServiceConfig {

    private val TAG = "MessagingServiceConfig_TAG"

    const val SUBSCRIBE_TO_ALL_TOPIC = "all"


    fun setupFCM() {
        /** [.token]
         * Requests the FCM registration token for this device.
         * This token is: A unique ID for this app install Used by your server to send push notifications to this device
         * */
        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d(TAG, "FCM Token: $token")

                    // الاشتراك في موضوع "all" بعد الحصول على التوكن
                    FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO_ALL_TOPIC)
                        .addOnCompleteListener { subTask ->
                            if (subTask.isSuccessful) {
                                Log.d(TAG, "✅ تم الاشتراك في قناة 'all' بنجاح")
                            } else {
                                Log.e(TAG, "❌ فشل الاشتراك في قناة 'all'", subTask.exception)
                            }
                        }
                } else {
                    Log.e(TAG, "❌ فشل الحصول على FCM Token", task.exception)
                }
            }
    }
}