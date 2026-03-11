package com.der3.shared.domain.use_case

import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.shared.domain.repo.MasbahaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMasbahaAzkarsUseCase {
    operator fun invoke(): Flow<List<MasbahaAzkar>>
}

class GetMasbahaAzkarsUseCaseImpl @Inject constructor(
    private val repository: MasbahaRepository
) : GetMasbahaAzkarsUseCase {
    override fun invoke(): Flow<List<MasbahaAzkar>> = repository.getLocalAzkars()
}