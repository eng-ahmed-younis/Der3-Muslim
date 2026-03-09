package com.der3.shared.domain.use_case

import com.der3.shared.domain.model.AzkarCategory
import com.der3.shared.domain.repo.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAzkarCategoriesUseCase {
    operator fun invoke(): Flow<List<AzkarCategory>>
}

class GetAzkarCategoriesUseCaseImpl
    @Inject
    constructor(
        private val azkarRepository: AzkarRepository,
    ) : GetAzkarCategoriesUseCase {
        override fun invoke(): Flow<List<AzkarCategory>> = azkarRepository.getAllCategories().flowOn(Dispatchers.IO)
    }
