package com.der3.shared.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.der3.shared.utils.DataBaseUtils

@Entity(tableName = DataBaseUtils.NOTIFICATION_TABLE)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val title: String,
    val body: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
