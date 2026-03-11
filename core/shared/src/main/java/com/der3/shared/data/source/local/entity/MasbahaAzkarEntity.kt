package com.der3.shared.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.der3.shared.utils.DataBaseUtils

@Entity(tableName = DataBaseUtils.MASBAHA_AZKAR_TABLE)
data class MasbahaAzkarEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val count: Int?
)