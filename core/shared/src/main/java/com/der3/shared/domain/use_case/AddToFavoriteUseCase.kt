package com.der3.shared.domain.use_case

import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.repo.FavoritesRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(favorite: FavoriteEntity) {
        repository.addFavorite(favorite)
    }
}
