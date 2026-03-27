package com.der3.shared.service.fcm

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.der3.data_store.api.DataStoreRepository
import com.der3.shared.service.fcm.data.MessageData
import com.der3.shared.service.fcm.notification.NotificationBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class Der3MuslimFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var notificationBuilder: NotificationBuilder

    companion object {
        private const val TAG = "Der3MuslimFirebaseMessagingService"
        private const val WAKE_LOCK_TAG = "Der3Muslim:NotificationWakeLock"
        private const val WAKE_LOCK_TIMEOUT = 30000L
    }

    private val json = Json { ignoreUnknownKeys = true }
    private var wakeLock: PowerManager.WakeLock? = null
    private lateinit var notificationLatch: CountDownLatch

    override fun onCreate() {
        super.onCreate()
        Log.w(TAG, "🚀 تم إنشاء خدمة الإشعارات")

        // تهيئة الـ CountDownLatch
        notificationLatch = CountDownLatch(1)

        // منع الجهاز من الدخول في وضع السكون خلال المعالجة
        try {
            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                WAKE_LOCK_TAG
            ).apply {
                setReferenceCounted(false)
                acquire(WAKE_LOCK_TIMEOUT) // 30 ثانية كحد أقصى
            }
            Log.d(TAG, "✅ تم تفعيل منع السكون")
        } catch (e: Exception) {
            Log.e(TAG, "❌ فشل في تفعيل منع السكون: ${e.message}")
        }
    }

    override fun handleIntent(intent: Intent) {
        Log.w(TAG, "📦 تم استلام Intent: ${intent.action}")

        try {
            val bundle = intent.extras
            if (bundle != null) {
                // التأكد من أن هذه رسالة FCM
                if (bundle.containsKey("gcm.message_id") ||
                    bundle.containsKey("google.message_id") ||
                    bundle.containsKey("message_id")) {

                    Log.w(TAG, "🎯 تم اكتشاف رسالة FCM")

                    // معالجة الرسالة بشكل متزامن وانتظار الانتهاء
                    processMessageSynchronously(RemoteMessage(bundle))

                    // عدم استدعاء super.handleIntent
                    return
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ خطأ في handleIntent: ${e.message}", e)
        }

        super.handleIntent(intent)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.w(TAG, "🔥 تم استلام رسالة أثناء تشغيل التطبيق")

        // معالجة الرسالة بشكل متزامن
        processMessageSynchronously(remoteMessage)
    }

    /**
     * معالجة الرسالة بشكل متزامن وانتظار الانتهاء قبل إنهاء الخدمة
     */
    private fun processMessageSynchronously(remoteMessage: RemoteMessage) {
        try {
            Log.d(TAG, "جاري معالجة الرسالة...")

            // استخراج بيانات الإشعار
            val title = remoteMessage.data["title"]
                ?: remoteMessage.notification?.title
                ?: "درع المسلم"

            val rawBody = remoteMessage.data["message"]
                ?: remoteMessage.data["body"]
                ?: remoteMessage.notification?.body
                ?: ""

            Log.d(TAG, "العنوان: $title")
            Log.d(TAG, "النص الأصلي: $rawBody")

            // معالجة النص إذا كان JSON
            val cleanMessage = if (rawBody.trim().startsWith("{")) {
                try {
                    json.decodeFromString<MessageData>(rawBody).message
                } catch (e: Exception) {
                    Log.e(TAG, "فشل تحليل JSON: ${e.message}")
                    rawBody
                }
            } else {
                rawBody
            }

            val type = (remoteMessage.data["type"] ?: "GENERAL").uppercase()
            val image = remoteMessage.data["image"] ?: remoteMessage.notification?.imageUrl?.toString()

            val messageData = MessageData(
                message = cleanMessage,
                type = type,
                image = image
            )

            val jsonBody = json.encodeToString(messageData)

            Log.d(TAG, "البيانات المعالجة: $jsonBody")

            // التحقق من تفعيل الإشعارات
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // عرض الإشعار وانتظار الانتهاء
                showNotificationAndWait(title, jsonBody)
            } else {
                Log.w(TAG, "⚠️ الإشعارات معطلة في التطبيق")
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ فشل في معالجة الإشعار: ${e.message}", e)
        } finally {
            // بعد الانتهاء من المعالجة، نحرر الموارد
            releaseResources()
            // نوقف الخدمة
            stopSelf()
        }
    }

    /**
     * عرض الإشعار وانتظار الانتهاء من العرض
     */
    private fun showNotificationAndWait(title: String, jsonBody: String) {
        try {
            // إعادة تهيئة CountDownLatch
            notificationLatch = CountDownLatch(1)

            // عرض الإشعار على الخيط الرئيسي
            Handler(Looper.getMainLooper()).post {
                try {
                    notificationBuilder.showNotification(
                        context = this@Der3MuslimFirebaseMessagingService,
                        title = title,
                        jsonBody = jsonBody
                    )
                    Log.w(TAG, "✅ تم عرض الإشعار بنجاح")
                } catch (e: Exception) {
                    Log.e(TAG, "❌ فشل في عرض الإشعار: ${e.message}", e)
                } finally {
                    // إطلاق الـ latch بعد الانتهاء من العرض
                    notificationLatch.countDown()
                }
            }

            // انتظار اكتمال عرض الإشعار (بحد أقصى 5 ثوانٍ)
            val completed = notificationLatch.await(5, TimeUnit.SECONDS)
            if (completed) {
                Log.d(TAG, "✅ اكتمل عرض الإشعار")
            } else {
                Log.w(TAG, "⚠️ انتهت مهلة انتظار عرض الإشعار")
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ خطأ في عرض الإشعار: ${e.message}", e)
            notificationLatch.countDown() // تحرير الـ latch في حالة الخطأ
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.w(TAG, "🆕 رمز FCM جديد: $token")

        // استخدام Coroutine للمهام غير الحرجة
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // حفظ الرمز في السيرفر
                saveTokenToServer(token)

                // الاشتراك في المواضيع
                FirebaseMessaging.getInstance().subscribeToTopic("all")
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "✅ تم الاشتراك في موضوع 'all'")
                        } else {
                            Log.e(TAG, "❌ فشل الاشتراك في الموضوع", task.exception)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "❌ فشل حفظ الرمز: ${e.message}")
            }
        }
    }

    private fun saveTokenToServer(token: String) {
        // أضف منطق حفظ الرمز في السيرفر الخاص بك
        Log.d(TAG, "💾 تم حفظ الرمز في السيرفر: $token")
    }

    private fun releaseResources() {
        try {
            if (wakeLock?.isHeld == true) {
                wakeLock?.release()
                Log.d(TAG, "🔓 تم تحرير منع السكون")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ خطأ في تحرير منع السكون: ${e.message}")
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "🗑️ تم إتلاف خدمة الإشعارات")
        releaseResources()
        super.onDestroy()
    }
}
