package com.der3.shared.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.der3.model.NotificationType
import com.der3.shared.utils.DataBaseUtils

@Entity(tableName = DataBaseUtils.NOTIFICATION_TABLE)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val title: String,
    val body: String,
    val type: String = NotificationType.GENERAL.value,
    // current time when receive notification
    val timestamp: Long = System.currentTimeMillis(),
    // Room uses a standard naming convention in the SQLite database.
    @ColumnInfo(name = "is_read")
    val isRead: Int = 0
)
