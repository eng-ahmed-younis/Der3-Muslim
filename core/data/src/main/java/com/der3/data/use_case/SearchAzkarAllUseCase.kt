package com.der3.data.use_case

import com.der3.data.repo.api.AzkarRepository
import com.der3.data.repo.api.AzkarSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchAzkarAllUseCase {
    operator fun invoke(query: String): Flow<AzkarSearchResult>
}

class SearchAzkarAllUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : SearchAzkarAllUseCase {
    override fun invoke(query: String): Flow<AzkarSearchResult> =
        repository.searchAll(query).flowOn(Dispatchers.IO)
}
