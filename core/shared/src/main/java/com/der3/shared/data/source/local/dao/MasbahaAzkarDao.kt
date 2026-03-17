package com.der3.shared.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.der3.shared.data.source.local.entity.MasbahaAzkarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MasbahaAzkarDao {

    @Query("SELECT * FROM masbaha_azkar_table")
    fun getAllAzkars(): Flow<List<MasbahaAzkarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAzkars(azkars: List<MasbahaAzkarEntity>)

    @Query("DELETE FROM masbaha_azkar_table")
    suspend fun deleteAllAzkars()

    @Query("SELECT COUNT(*) FROM masbaha_azkar_table")
    fun getAzkarCount(): Flow<Int>
}