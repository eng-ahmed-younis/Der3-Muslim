package com.der3.shared.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.der3.shared.data.source.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification_table ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("DELETE FROM notification_table WHERE id = :id")
    suspend fun deleteNotificationById(id: String)

    @Query("UPDATE notification_table SET is_read = 1 WHERE id = :id")
    suspend fun markAsRead(id: String)

    @Query("DELETE FROM notification_table")
    suspend fun deleteAllNotifications()

    @Query("DELETE FROM notification_table WHERE type = :type")
    suspend fun deleteNotificationsByType(type: String)

    @Query("SELECT * FROM notification_table WHERE type = :type ORDER BY timestamp DESC LIMIT 1")
    fun getNotificationByType(type: String): Flow<NotificationEntity?>


    // read status

    /** mark all notifications as read */
    @Query("UPDATE notification_table SET is_read = 1")
    suspend fun markAllNotificationsAsRead()

    /** 0 = unread | 1 = read */
    @Query("SELECT COUNT(*) FROM notification_table WHERE is_read = 0")
    fun getUnreadNotificationsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM notification_table WHERE is_read = 1")
    fun getReadNotificationsCount(): Flow<Int>


}
