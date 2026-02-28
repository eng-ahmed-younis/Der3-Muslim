package com.der3.data.use_case

import com.der3.data.model.AzkarCategory
import com.der3.data.repo.api.AzkarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GetAzkarCategoriesUseCase {
    operator fun invoke(): Flow<List<AzkarCategory>>
}

class GetAzkarCategoriesUseCaseImpl @Inject constructor(
    private val azkarRepository: AzkarRepository
) : GetAzkarCategoriesUseCase{
    override fun invoke(): Flow<List<AzkarCategory>> {
        return azkarRepository.getAllCategories().flowOn(Dispatchers.IO)
    }

}