package com.der3.shared.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.der3.shared.utils.DataBaseUtils

@Entity(tableName = DataBaseUtils.RECYCLE_BIN_TABLE)
data class RecycleBinEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val text: String,
    val audioPath: String,
    val repeatCount: Int,
    val deletedAt: Long,
    val categoryName: String? = null
)
