package com.der3.shared.domain.repo

import com.der3.shared.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    suspend fun addFavorite(favorite: FavoriteEntity)
    suspend fun removeFavorite(id: Int)
    fun isFavorite(id: Int): Flow<Boolean>
}
