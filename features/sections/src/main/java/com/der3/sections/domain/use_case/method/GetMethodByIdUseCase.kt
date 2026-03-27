package com.der3.sections.domain.use_case.method

import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetMethodByIdUseCase {
    operator fun invoke(methodId: Int): Flow<PrayerTimesResult<PrayerMethod?>>
}

class GetMethodByIdUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetMethodByIdUseCase {

    override fun invoke(methodId: Int): Flow<PrayerTimesResult<PrayerMethod?>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getCalculationMethods()
        when (result) {
            is IPrayerRepository.Result.Success -> {
                val method = result.data.find { it.id == methodId }
                emit(PrayerTimesResult.Success(method))
            }
            is IPrayerRepository.Result.Error -> {
                emit(PrayerTimesResult.Error(result.message, result.code))
            }
            is IPrayerRepository.Result.Loading -> {
                emit(PrayerTimesResult.Loading)
            }
        }
    }
}