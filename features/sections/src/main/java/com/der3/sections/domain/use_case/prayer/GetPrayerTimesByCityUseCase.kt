package com.der3.sections.domain.use_case.prayer

import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetPrayerTimesByCityUseCase {
    operator fun invoke(city: String, country: String, state: String?, method: Int): Flow<PrayerTimesResult<PrayerTimes>>
}

class GetPrayerTimesByCityUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetPrayerTimesByCityUseCase {

    override fun invoke(
        city: String,
        country: String,
        state: String?,
        method: Int
    ): Flow<PrayerTimesResult<PrayerTimes>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getPrayerTimesByCity(
            city = city,
            country = country,
            state = state,
            method = method,
            school = 0
        )

        emit(result.toPrayerTimesResult())
    }
}