package com.der3.data.use_case

import com.der3.data.repo.api.AzkarItemWithCategory
import com.der3.data.repo.api.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAzkarItemsByCountUseCase {
    operator fun invoke(count: Int): Flow<List<AzkarItemWithCategory>>
}

class GetAzkarItemsByCountUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : GetAzkarItemsByCountUseCase {
    override fun invoke(count: Int): Flow<List<AzkarItemWithCategory>> =
        repository.getItemsByCount(count).flowOn(Dispatchers.IO)
}