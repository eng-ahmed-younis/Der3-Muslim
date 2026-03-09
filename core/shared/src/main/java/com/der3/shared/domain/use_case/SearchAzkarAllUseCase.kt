package com.der3.shared.domain.use_case

import com.der3.shared.domain.repo.AzkarRepository
import com.der3.shared.domain.repo.AzkarSearchResult
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
