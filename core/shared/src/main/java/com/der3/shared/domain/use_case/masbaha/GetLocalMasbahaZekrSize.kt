package com.der3.shared.domain.use_case.masbaha

import com.der3.shared.domain.repo.MasbahaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetLocalMasbahaZekrSize {
    operator fun invoke() : Flow<Int>
}



class GetLocalMasbahaZekrSizeImpl @Inject constructor(
    private val repository: MasbahaRepository
) : GetLocalMasbahaZekrSize {
    override fun invoke(): Flow<Int> = repository.getLocalAzkarSize()
}