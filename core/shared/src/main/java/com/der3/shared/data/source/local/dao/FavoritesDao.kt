package com.der3.shared.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.der3.shared.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites_table")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_table WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Query("SELECT * FROM favorites_table WHERE id = :id")
    suspend fun getFavoriteById(id: Int): FavoriteEntity?
}
