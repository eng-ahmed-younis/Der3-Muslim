package com.der3.sections.domain.use_case.location

import com.der3.sections.data.repository.PrayerRepositoryImpl
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetMultipleLocationsPrayerTimesUseCase {
    operator fun invoke(locations: List<Pair<Double, Double>>, method: Int): Flow<PrayerTimesResult<Map<Pair<Double, Double>, PrayerTimes>>>
}

class GetMultipleLocationsPrayerTimesUseCaseImpl @Inject constructor(
    private val repository: PrayerRepositoryImpl
) : GetMultipleLocationsPrayerTimesUseCase {

    override fun invoke(
        locations: List<Pair<Double, Double>>,
        method: Int
    ): Flow<PrayerTimesResult<Map<Pair<Double, Double>, PrayerTimes>>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getPrayerTimesForMultipleLocations(locations, method)
        emit(result.toPrayerTimesResult())
    }
}