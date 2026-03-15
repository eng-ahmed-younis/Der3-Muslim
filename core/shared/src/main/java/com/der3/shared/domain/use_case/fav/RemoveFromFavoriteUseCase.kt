package com.der3.shared.domain.use_case.fav

import com.der3.shared.domain.repo.FavoritesRepository
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.removeFavorite(id)
    }
}