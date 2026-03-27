package com.der3.sections.domain.use_case.calender

import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface GetHijriYearlyCalendarUseCase {
    operator fun invoke(year: Int, latitude: Double, longitude: Double, method: Int): Flow<PrayerTimesResult<Map<String, List<PrayerTimes>>>>
}

class GetHijriYearlyCalendarUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetHijriYearlyCalendarUseCase {

    override fun invoke(
        year: Int,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Flow<PrayerTimesResult<Map<String, List<PrayerTimes>>>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getHijriYearlyCalendar(year, latitude, longitude, method, 0)
        emit(result.toPrayerTimesResult())
    }
}
