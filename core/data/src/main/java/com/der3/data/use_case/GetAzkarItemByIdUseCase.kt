package com.der3.data.use_case

import com.der3.data.model.AzkarItem
import com.der3.data.repo.api.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAzkarItemByIdUseCase {
    operator fun invoke(categoryId: Int, itemId: Int): Flow<AzkarItem?>
}

class GetAzkarItemByIdUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : GetAzkarItemByIdUseCase {
    override fun invoke(categoryId: Int, itemId: Int): Flow<AzkarItem?> =
        repository.getItemById(categoryId, itemId).flowOn(Dispatchers.IO)
}