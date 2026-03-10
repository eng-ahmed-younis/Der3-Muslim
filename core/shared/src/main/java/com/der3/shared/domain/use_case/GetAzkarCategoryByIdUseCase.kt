package com.der3.shared.domain.use_case

import com.der3.shared.domain.model.AzkarCategory
import com.der3.shared.domain.repo.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAzkarCategoryByIdUseCase {
    operator fun invoke(id: Int): Flow<AzkarCategory?>
}

class GetAzkarCategoryByIdUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : GetAzkarCategoryByIdUseCase {
    override fun invoke(id: Int): Flow<AzkarCategory?> =
        repository.getCategoryById(id).flowOn(Dispatchers.IO)
}