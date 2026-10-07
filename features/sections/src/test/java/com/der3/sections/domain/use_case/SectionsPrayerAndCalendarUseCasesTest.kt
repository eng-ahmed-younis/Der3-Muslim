package com.der3.sections.domain.use_case

import app.cash.turbine.test
import com.der3.sections.domain.model.NextPrayerInfo
import com.der3.sections.domain.model.PrayerTimesResult
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.sections.domain.use_case.prayer.GetCurrentPrayerUseCaseImpl
import com.der3.sections.domain.use_case.prayer.GetNextPrayerUseCaseImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SectionsPrayerAndCalendarUseCasesTest {

    private val repository: IPrayerRepository = mockk()

    @Test
    fun `GetNextPrayerUseCaseImpl maps success result from repository`() = runTest {
        val useCase = GetNextPrayerUseCaseImpl(repository)
        val info = NextPrayerInfo(
            prayerName = "Fajr",
            prayerTime = "04:30",
            prayerTimeAmPm = "AM",
            gregorianDate = "2026-03-30",
            hijriDate = "1447-10-11"
        )
        every { repository.observeNextPrayer(21.4, 39.8, 4, 0) } returns flowOf(IPrayerRepository.Result.Success(info))

        useCase.invoke(21.4, 39.8, 4, 0).test {
            val result = awaitItem()
            assertTrue(result is PrayerTimesResult.Success)
            assertEquals("Fajr", (result as PrayerTimesResult.Success).data.prayerName)
            awaitComplete()
        }
    }

    @Test
    fun `GetCurrentPrayerUseCaseImpl returns loading then result`() = runTest {
        val useCase = GetCurrentPrayerUseCaseImpl(repository)
        val currentInfo = IPrayerRepository.CurrentPrayerInfo(
            name = "Dhuhr",
            time = "12:15",
            endsAt = "15:30",
            remainingTime = "03:15:00"
        )
        coEvery { repository.getCurrentPrayer(21.4, 39.8, 4, 0) } returns IPrayerRepository.Result.Success(currentInfo)

        useCase.invoke(21.4, 39.8, 4, 0).test {
            assertEquals(PrayerTimesResult.Loading, awaitItem())
            val success = awaitItem()
            assertTrue(success is PrayerTimesResult.Success)
            assertEquals("Dhuhr", (success as PrayerTimesResult.Success).data.name)
            awaitComplete()
        }
    }
}
