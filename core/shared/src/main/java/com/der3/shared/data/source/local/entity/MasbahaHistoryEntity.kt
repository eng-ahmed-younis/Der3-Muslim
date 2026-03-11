package com.der3.shared.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "masbaha_history")
data class MasbahaHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val zekrId: Int?,
    val zekrText: String,
    val count: Int,
    val timestamp: Long = System.currentTimeMillis()
)
