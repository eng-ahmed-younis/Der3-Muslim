package com.der3.shared.data.repo

import com.der3.shared.data.source.local.dao.FavoritesDao
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.repo.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoritesDao.getAllFavorites()
    }

    override suspend fun addFavorite(favorite: FavoriteEntity) {
        favoritesDao.insertFavorite(favorite)
    }

    override suspend fun removeFavorite(id: Int) {
        favoritesDao.deleteFavoriteById(id)
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return favoritesDao.isFavorite(id)
    }
}
