package com.der3.shared.domain.use_case

import com.der3.shared.domain.repo.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = repository.isFavorite(id)
}
