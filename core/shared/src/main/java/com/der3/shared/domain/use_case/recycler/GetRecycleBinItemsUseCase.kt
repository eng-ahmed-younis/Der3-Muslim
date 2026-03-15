package com.der3.shared.domain.use_case.recycler

import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.repo.RecycleBinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetRecycleBinItemsUseCase {
    operator fun invoke(): Flow<List<RecycleBinEntity>>
}

class GetRecycleBinItemsUseCaseImpl @Inject constructor(
    private val repository: RecycleBinRepository
) : GetRecycleBinItemsUseCase {
    override fun invoke(): Flow<List<RecycleBinEntity>> =
        repository.getAllDeletedItems().flowOn(Dispatchers.IO)
}
