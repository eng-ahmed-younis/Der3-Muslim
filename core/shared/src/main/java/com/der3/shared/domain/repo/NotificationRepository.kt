package com.der3.shared.domain.repo

import com.der3.shared.data.source.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getAllNotifications(): Flow<List<NotificationEntity>>
    suspend fun insertNotification(notification: NotificationEntity)
    suspend fun deleteNotificationById(id: String)
    suspend fun markAsRead(id: String)
    suspend fun deleteAllNotifications()
    suspend fun deleteNotificationsByType(type: String)
    fun getNotificationByType(type: String): Flow<NotificationEntity?>



    // read status
    fun getUnreadNotificationsCount(): Flow<Int>
    fun getReadNotificationsCount(): Flow<Int>
    suspend fun markAllNotificationsAsRead()
}
