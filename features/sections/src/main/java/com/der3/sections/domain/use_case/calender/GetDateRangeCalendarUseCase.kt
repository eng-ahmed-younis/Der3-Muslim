package com.der3.sections.domain.use_case.calender

import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface GetDateRangeCalendarUseCase {
    operator fun invoke(startDate: String, endDate: String, latitude: Double, longitude: Double, method: Int): Flow<PrayerTimesResult<List<PrayerTimes>>>
}

class GetDateRangeCalendarUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetDateRangeCalendarUseCase {

    override fun invoke(
        startDate: String,
        endDate: String,
        latitude: Double,
        longitude: Double,
        method: Int
    ): Flow<PrayerTimesResult<List<PrayerTimes>>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getPrayerTimesForDateRange(startDate, endDate, latitude, longitude, method, 0)
        emit(result.toPrayerTimesResult())
    }
}