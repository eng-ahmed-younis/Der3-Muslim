package com.der3.shared.service.fcm.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.os.Build
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.AppStyle
import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.use_case.notification.InsertNotificationUseCase
import com.der3.shared.service.fcm.data.MessageData
import com.der3.ui.R
import com.der3.ui.isDarkTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class NotificationBuilder @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val insertNotificationUseCase: InsertNotificationUseCase
) {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    companion object {
        private const val CHANNEL_ID = "der3_notification_channel"
        private const val CHANNEL_NAME = "Der3 Notifications"

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val soundUri =
                    (ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/raw/click").toUri()

                val channel =
                    notificationManager.getNotificationChannel(CHANNEL_ID) ?: NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        description = "General notifications for Der3 Muslim"
                        val audioAttributes = AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
                        setSound(soundUri, audioAttributes)
                    }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    suspend fun showNotification(
        context: Context,
        title: String,
        jsonBody: String
    ) {
        val messageData: MessageData = MessageData.fromJson(jsonString = jsonBody)
        val notificationId = UUID.randomUUID().toString()

        scope.launch {
            insertNotificationUseCase.invoke(
                notification = NotificationEntity(
                    id = notificationId,
                    title = title,
                    type = messageData.type,
                    body = messageData.message,
                    timestamp = System.currentTimeMillis()
                )
            )
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val appStyle = AppStyle.valueOf(dataStoreRepository.appStyle)

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(getSoundUri(context))
                .setAutoCancel(true)
                .setContentIntent(
                    createPendingIntent(
                        context = context,
                        notificationId = notificationId
                    )
                ).setColor(context.getColor(R.color.notification_color))
                .setCustomContentView(
                    configSmallerMessage(
                        context = context,
                        title = title,
                        description = messageData.message,
                        notificationStyle = setNotificationStyle(
                            context = context, appStyle = appStyle
                        )
                    )
                ).setStyle(NotificationCompat.DecoratedCustomViewStyle()).build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun getSoundUri(context: Context): Uri {
        return (ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/raw/click").toUri()
    }

    private fun configSmallerMessage(
        context: Context, title: String, description: String, notificationStyle: NotificationStyle
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_small).apply {
            setTextViewText(R.id.notification_title_small, title)
            setTextViewText(R.id.notification_desc_small, description)
            setTextColor(
                R.id.notification_title_small, context.getColor(notificationStyle.titleColor)
            )
            setTextColor(
                R.id.notification_desc_small, context.getColor(notificationStyle.descriptionColor)
            )
        }
    }

    private fun configLargeMessage(
        context: Context, title: String, description: String, image: Bitmap? = null

    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_large).apply {
            setTextViewText(R.id.notification_title, title)
            setTextViewText(R.id.notification_desc, description)
            setImageViewResource(R.id.notification_icon, R.drawable.ic_launcher_foreground)
            if (image != null) {
                setImageViewBitmap(R.id.notification_big_image, image)
            }
        }
    }

/*    private fun createPendingIntent(
        context: Context, notificationId: Long
    ): PendingIntent {
        // الرابط اللي حددته في الـ Manifest

        val deepLinkUri = "https://der3.muslim.deeplink/notifications/$notificationId".toUri()

        val intent: Intent? =
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        intent?.apply {
            action = Intent.ACTION_VIEW
            data = deepLinkUri
        }

        return PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }*/


    private fun createPendingIntent(
        context: Context,
        notificationId: String
    ): PendingIntent {

        val intent = Intent(Intent.ACTION_VIEW,
            "https://der3.muslim.deeplink/notifications/$notificationId".toUri()).apply {
            `package` = context.packageName
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        return PendingIntent.getActivity(
            context,
            notificationId.hashCode(), // Unique ID here is critical
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }



    private fun setNotificationStyle(context: Context, appStyle: AppStyle): NotificationStyle {
        return when (appStyle) {
            AppStyle.DARK -> NotificationStyle(
                titleColor = R.color.white, descriptionColor = R.color.white
            )

            AppStyle.LIGHT -> NotificationStyle(
                titleColor = R.color.black, descriptionColor = R.color.black
            )

            AppStyle.SYSTEM -> if (context.isDarkTheme()) {
                NotificationStyle(
                    titleColor = R.color.white, descriptionColor = R.color.white
                )
            } else {
                NotificationStyle(
                    titleColor = R.color.black, descriptionColor = R.color.black
                )
            }
        }
    }
}

data class NotificationStyle(
    val titleColor: Int, val descriptionColor: Int
)
