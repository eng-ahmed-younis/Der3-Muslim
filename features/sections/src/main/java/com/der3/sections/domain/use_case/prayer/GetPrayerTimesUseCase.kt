package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.PrayerTimesParams
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetPrayerTimesUseCase {
    operator fun invoke(params: PrayerTimesParams): Flow<PrayerTimesResult<PrayerTimes>>
}

class GetPrayerTimesUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetPrayerTimesUseCase {

    override fun invoke(params: PrayerTimesParams): Flow<PrayerTimesResult<PrayerTimes>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = when (params) {
            is PrayerTimesParams.ByCoordinates -> {
                repository.getTodayPrayerTimes(
                    latitude = params.latitude,
                    longitude = params.longitude,
                    method = params.method,
                    school = params.school
                )
            }
            is PrayerTimesParams.ByDate -> {
                repository.getPrayerTimesByDate(
                    date = params.date,
                    latitude = params.latitude,
                    longitude = params.longitude,
                    method = params.method,
                    school = params.school
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid params type")
            }
        }

        emit(result.toPrayerTimesResult())
    }
}