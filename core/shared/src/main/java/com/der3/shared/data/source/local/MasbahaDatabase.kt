package com.der3.shared.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.der3.shared.data.source.local.dao.MasbahaAzkarDao
import com.der3.shared.data.source.local.dao.MasbahaHistoryDao
import com.der3.shared.data.source.local.entity.MasbahaAzkarEntity
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity
import com.der3.shared.utils.DataBaseUtils

@Database(
    entities = [
        MasbahaAzkarEntity::class,
        MasbahaHistoryEntity::class
    ],
    version = DataBaseUtils.DATABASE_VERSION,
    exportSchema = false
)
abstract class MasbahaDatabase : RoomDatabase() {
    abstract fun masbahaAzkarDao(): MasbahaAzkarDao
    abstract fun masbahaHistoryDao(): MasbahaHistoryDao
}
