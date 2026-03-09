package com.der3.shared.domain.use_case

import com.der3.shared.domain.model.AzkarItem
import com.der3.shared.domain.repo.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchAzkarItemsUseCase {
    operator fun invoke(query: String): Flow<List<AzkarItem>>
}

class SearchAzkarItemsUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : SearchAzkarItemsUseCase {
    override fun invoke(query: String): Flow<List<AzkarItem>> =
        repository.searchItems(query).flowOn(Dispatchers.IO)
}