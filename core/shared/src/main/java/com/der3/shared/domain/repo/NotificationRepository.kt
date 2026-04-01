package com.der3.shared.domain.repo

import com.der3.shared.data.source.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getAllNotifications(): Flow<List<NotificationEntity>>
    suspend fun insertNotification(notification: NotificationEntity)
    suspend fun deleteNotificationById(id: String)
    suspend fun markAsRead(id: Int)
    suspend fun deleteAllNotifications()
    fun getNotificationByType(type: String): Flow<NotificationEntity?>
}
