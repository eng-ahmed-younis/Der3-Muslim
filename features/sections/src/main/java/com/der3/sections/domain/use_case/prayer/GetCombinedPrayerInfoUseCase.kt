package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.CombinedPrayerInfo
import com.der3.sections.domain.model.PrayerTimesResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GetCombinedPrayerInfoUseCase {
    operator fun invoke(latitude: Double, longitude: Double, method: Int, school: Int): Flow<PrayerTimesResult<CombinedPrayerInfo>>
}

class GetCombinedPrayerInfoUseCaseImpl @Inject constructor(
    private val getNextPrayerUseCase: GetNextPrayerUseCase,
    private val getAllPrayersWithStatusUseCase: GetAllPrayersWithStatusUseCase
) : GetCombinedPrayerInfoUseCase {

    override fun invoke(
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): Flow<PrayerTimesResult<CombinedPrayerInfo>> {
        return combine(
            getNextPrayerUseCase(latitude, longitude, method, school),
            getAllPrayersWithStatusUseCase(latitude, longitude, method, school)
        ) { nextResult, allResult ->
            when {
                nextResult is PrayerTimesResult.Success && allResult is PrayerTimesResult.Success -> {
                    PrayerTimesResult.Success(
                        CombinedPrayerInfo(
                            nextPrayer = nextResult.data,
                            allPrayers = allResult.data
                        )
                    )
                }
                nextResult is PrayerTimesResult.Error -> PrayerTimesResult.Error(nextResult.message)
                allResult is PrayerTimesResult.Error -> PrayerTimesResult.Error(allResult.message)
                else -> PrayerTimesResult.Loading
            }
        }
    }
}