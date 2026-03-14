package com.der3.shared.domain.use_case

import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.repo.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavouritesUsecase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<FavoriteEntity>> {
        return repository.getAllFavorites()
    }
}
