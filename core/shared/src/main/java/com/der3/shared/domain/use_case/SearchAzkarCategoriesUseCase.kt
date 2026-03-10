package com.der3.shared.domain.use_case

import com.der3.shared.domain.model.AzkarCategory
import com.der3.shared.domain.repo.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SearchAzkarCategoriesUseCase {
    operator fun invoke(query: String): Flow<List<AzkarCategory>>
}

class SearchAzkarCategoriesUseCaseImpl @Inject constructor(
    private val repository: AzkarRepository
) : SearchAzkarCategoriesUseCase {
    override fun invoke(query: String): Flow<List<AzkarCategory>> =
        repository.searchCategories(query).flowOn(Dispatchers.IO)
}