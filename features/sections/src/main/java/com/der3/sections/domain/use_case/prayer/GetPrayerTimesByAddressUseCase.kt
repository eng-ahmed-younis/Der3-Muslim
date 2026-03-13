package com.der3.sections.domain.use_case.prayer


import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.model.toPrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.shared.domain.model.prayer.PrayerTimes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetPrayerTimesByAddressUseCase {
    operator fun invoke(address: String, method: Int): Flow<PrayerTimesResult<PrayerTimes>>
}

class GetPrayerTimesByAddressUseCaseImpl @Inject constructor(
    private val repository: IPrayerRepository
) : GetPrayerTimesByAddressUseCase {

    override fun invoke(address: String, method: Int): Flow<PrayerTimesResult<PrayerTimes>> = flow {
        emit(PrayerTimesResult.Loading)

        val result = repository.getPrayerTimesByAddress(
            address = address,
            method = method,
            school = 0
        )

        emit(result.toPrayerTimesResult())
    }
}