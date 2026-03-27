package com.der3.shared.domain.use_case.recycler

import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.repo.RecycleBinRepository
import javax.inject.Inject

interface InsertToRecycleBinUseCase {
    suspend operator fun invoke(item: RecycleBinEntity)
}

class InsertToRecycleBinUseCaseImpl @Inject constructor(
    private val repository: RecycleBinRepository
) : InsertToRecycleBinUseCase {
    override suspend fun invoke(item: RecycleBinEntity) {
        repository.insertToRecycleBin(item)
    }
}
