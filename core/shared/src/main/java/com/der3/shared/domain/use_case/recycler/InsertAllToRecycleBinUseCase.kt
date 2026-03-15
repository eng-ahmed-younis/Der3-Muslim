package com.der3.shared.domain.use_case.recycler

import com.der3.shared.data.source.local.entity.RecycleBinEntity
import com.der3.shared.domain.repo.RecycleBinRepository
import javax.inject.Inject

interface InsertAllToRecycleBinUseCase {
    suspend operator fun invoke(items: List<RecycleBinEntity>)
}

class InsertAllToRecycleBinUseCaseImpl @Inject constructor(
    private val repository: RecycleBinRepository
) : InsertAllToRecycleBinUseCase {
    override suspend fun invoke(items: List<RecycleBinEntity>) {
        repository.insertAll(items)
    }
}
