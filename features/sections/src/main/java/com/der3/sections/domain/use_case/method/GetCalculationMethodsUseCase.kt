package com.der3.sections.domain.use_case.method

import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetCalculationMethodsUseCase {
    operator fun invoke(): Flow<PrayerTimesResult<List<PrayerMethod>>>
}

class GetCalculationMethodsUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetCalculationMethodsUseCase {

    override fun invoke(): Flow<PrayerTimesResult<List<PrayerMethod>>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getCalculationMethods()
        emit(result.toPrayerTimesResult())
    }
}