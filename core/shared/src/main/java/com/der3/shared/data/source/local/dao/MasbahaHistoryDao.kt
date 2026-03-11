package com.der3.shared.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.der3.shared.data.source.local.entity.MasbahaHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MasbahaHistoryDao {

    @Query("SELECT * FROM masbaha_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<MasbahaHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: MasbahaHistoryEntity)

    @Query("SELECT SUM(count) FROM masbaha_history")
    fun getTotalCount(): Flow<Int?>

    @Query("DELETE FROM masbaha_history")
    suspend fun clearHistory()
}
