package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetAllPrayersWithStatusUseCase {
    operator fun invoke(latitude: Double, longitude: Double, method: Int, school: Int): Flow<PrayerTimesResult<List<IPrayerRepository.PrayerWithStatus>>>
}

class GetAllPrayersWithStatusUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetAllPrayersWithStatusUseCase {

    override fun invoke(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Flow<PrayerTimesResult<List<IPrayerRepository.PrayerWithStatus>>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getAllPrayersWithStatus(latitude, longitude, method, school)
        emit(result.toPrayerTimesResult())
    }
}
