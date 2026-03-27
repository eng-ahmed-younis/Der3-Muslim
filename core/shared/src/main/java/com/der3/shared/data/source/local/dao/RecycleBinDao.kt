package com.der3.shared.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import kotlinx.coroutines.flow.Flow

// ASC -> oldest → newest
// DESC -> newest → oldest
@Dao
interface RecycleBinDao {
    @Query("SELECT * FROM recycle_bin_table ORDER BY deletedAt DESC")
    fun getAllDeletedItems(): Flow<List<RecycleBinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRecycleBin(item: RecycleBinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RecycleBinEntity>)

    @Query("DELETE FROM recycle_bin_table WHERE id = :id")
    suspend fun deletePermanently(id: Int)

    @Query("DELETE FROM recycle_bin_table")
    suspend fun clearRecycleBin()

    @Query("SELECT * FROM recycle_bin_table WHERE id = :id")
    suspend fun getItemById(id: Int): RecycleBinEntity?
}
