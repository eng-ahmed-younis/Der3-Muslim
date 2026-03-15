package com.der3.shared.domain.use_case.recycler

import com.der3.shared.domain.repo.RecycleBinRepository
import javax.inject.Inject

interface DeleteRecyclerItemByIdUseCase {
    suspend operator fun invoke(id: Int)
}

class DeleteRecyclerItemByIdUseCaseImpl @Inject constructor(
    private val repository: RecycleBinRepository
) : DeleteRecyclerItemByIdUseCase {
    override suspend fun invoke(id: Int) {
        repository.deleteItemById(id)
    }
}
