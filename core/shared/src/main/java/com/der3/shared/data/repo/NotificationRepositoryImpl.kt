package com.der3.shared.data.repo

import com.der3.shared.data.source.local.dao.NotificationDao
import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.repo.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override fun getAllNotifications(): Flow<List<NotificationEntity>> {
        return notificationDao.getAllNotifications()
    }

    override suspend fun insertNotification(notification: NotificationEntity) {
        return notificationDao.insertNotification(notification)
    }

    override suspend fun deleteNotificationById(id: String) {
        notificationDao.deleteNotificationById(id)
    }

    override suspend fun markAsRead(id: Int) {
        notificationDao.markAsRead(id)
    }

    override suspend fun deleteAllNotifications() {
        notificationDao.deleteAllNotifications()
    }

    override suspend fun deleteNotificationsByType(type: String) {
        notificationDao.deleteNotificationsByType(type)
    }

    override fun getNotificationByType(type: String): Flow<NotificationEntity?> {
        return notificationDao.getNotificationByType(type)
    }
}
