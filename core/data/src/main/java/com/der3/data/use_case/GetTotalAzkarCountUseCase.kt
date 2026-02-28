package com.der3.data.use_case

import com.der3.data.repo.api.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetTotalAzkarCountUseCase {
    operator fun invoke(): Flow<Int>
}

class GetTotalAzkarCountUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : GetTotalAzkarCountUseCase {
    override fun invoke(): Flow<Int> =
        repository.getTotalAzkarCount().flowOn(Dispatchers.IO)
}