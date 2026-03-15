package com.der3.shared.domain.use_case.recycler

import com.der3.shared.domain.repo.RecycleBinRepository
import javax.inject.Inject

interface ClearRecycleBinUseCase {
    suspend operator fun invoke()
}

class ClearRecycleBinUseCaseImpl @Inject constructor(
    private val repository: RecycleBinRepository
) : ClearRecycleBinUseCase {
    override suspend fun invoke() {
        repository.clearRecycleBin()
    }
}
