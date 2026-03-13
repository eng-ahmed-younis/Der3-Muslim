package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetCurrentPrayerUseCase {
    operator fun invoke(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int = 0
    ): Flow<PrayerTimesResult<IPrayerRepository.CurrentPrayerInfo>>
}

class GetCurrentPrayerUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetCurrentPrayerUseCase {

    override fun invoke(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Flow<PrayerTimesResult<IPrayerRepository.CurrentPrayerInfo>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getCurrentPrayer(latitude, longitude, method, school)
        emit(result.toPrayerTimesResult())
    }
}