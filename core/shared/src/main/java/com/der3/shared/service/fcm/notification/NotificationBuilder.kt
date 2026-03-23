package com.der3.shared.service.fcm.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.os.Build
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.der3.shared.service.fcm.data.MessageData
import com.der3.shared.utils.stringToBitmap
import com.der3.ui.R

object NotificationBuilder {

    private const val CHANNEL_ID = "der3_notification_channel"
    private const val CHANNEL_NAME = "Der3 Notifications"

    fun showNotification(context: Context, title: String, jsonBody: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val messageData: MessageData = MessageData.fromJson(jsonString = jsonBody)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.moon)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.moon))
            .setContentTitle(title)
            .setContentText(messageData.message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(getSoundUri(context))
            .setAutoCancel(true)
            .setColor(context.getColor(R.color.notification_color))
            .setCustomContentView(
                configSmallerMessage(
                    context = context,
                    title = title,
                    description = messageData.message,
                    image = messageData.image?.stringToBitmap()
                )
            )
            .setCustomBigContentView(configLargeMessage(
                context = context,
                title = title,
                description = messageData.message,
                image = messageData.image?.stringToBitmap()
            ))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val soundUri =
                (ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/raw/click").toUri()

            val channel =
                notificationManager.getNotificationChannel(CHANNEL_ID) ?: NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "General notifications for Der3 Muslim"
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build()
                    setSound(soundUri, audioAttributes)
                }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getSoundUri(context: Context): Uri {
        return (ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/raw/click").toUri()
    }

    private fun configSmallerMessage(
        context: Context,
        title: String,
        description: String,
        image: Bitmap? = null
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_small).apply {
            setTextViewText(R.id.notification_title, title)
            setTextViewText(R.id.notification_desc, description)
            if (image != null) {
                setImageViewBitmap(R.id.notification_icon, image)
            } else {
                setImageViewResource(
                    R.id.notification_icon, R.drawable.ic_launcher_foreground
                )
            }

        }
    }

    private fun configLargeMessage(
        context: Context,
        title: String,
        description: String,
        image: Bitmap? = null

    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_large).apply {
            setTextViewText(R.id.notification_title, title)
            setTextViewText(R.id.notification_desc, description)
            setImageViewResource(R.id.notification_icon, R.drawable.ic_launcher_foreground)
            if (image != null) {
                setImageViewBitmap(R.id.notification_big_image, image)
            } /*else {
                setImageViewResource(R.id.notification_big_image, R.drawable.bismillah)
            }*/
        }
    }


}
