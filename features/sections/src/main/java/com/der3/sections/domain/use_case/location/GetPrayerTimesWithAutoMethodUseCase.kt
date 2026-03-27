package com.der3.sections.domain.use_case.location

import com.der3.sections.data.repository.PrayerRepositoryImpl
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetPrayerTimesWithAutoMethodUseCase {
    operator fun invoke(latitude: Double, longitude: Double): Flow<PrayerTimesResult<PrayerTimes>>
}

class GetPrayerTimesWithAutoMethodUseCaseImpl @Inject constructor(
    private val repository: PrayerRepositoryImpl
) : GetPrayerTimesWithAutoMethodUseCase {

    override fun invoke(latitude: Double, longitude: Double): Flow<PrayerTimesResult<PrayerTimes>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getPrayerTimesWithAutoMethod(latitude, longitude)
        emit(result.toPrayerTimesResult())
    }
}