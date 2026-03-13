package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.NextPrayerInfo
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetNextPrayerUseCase {
    operator fun invoke(latitude: Double, longitude: Double, method: Int): Flow<PrayerTimesResult<NextPrayerInfo>>
}

class GetNextPrayerUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetNextPrayerUseCase {

    override fun invoke(
        latitude: Double,
        longitude: Double,
        method: Int
    ): Flow<PrayerTimesResult<NextPrayerInfo>> {
        return repository.observeNextPrayer(latitude, longitude, method)
            .map { result ->
                when (result) {
                    is IPrayerRepository.Result.Success -> PrayerTimesResult.Success(result.data)
                    is IPrayerRepository.Result.Error -> PrayerTimesResult.Error(result.message, result.code)
                    is IPrayerRepository.Result.Loading -> PrayerTimesResult.Loading
                }
            }
    }
}
