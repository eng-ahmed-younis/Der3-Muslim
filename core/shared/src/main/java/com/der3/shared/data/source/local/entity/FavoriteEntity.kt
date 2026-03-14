package com.der3.shared.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.der3.shared.utils.DataBaseUtils

@Entity(tableName = DataBaseUtils.FAVORITES_TABLE)
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val audioPath: String,
    val repeatCount: Int,
    val categoryName: String? = null
)
