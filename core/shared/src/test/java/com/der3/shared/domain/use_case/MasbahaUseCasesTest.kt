package com.der3.shared.domain.use_case

import app.cash.turbine.test
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.SyncState
import com.der3.shared.domain.model.MasbahaAzkar
import com.der3.shared.domain.repo.MasbahaRepository
import com.der3.shared.domain.use_case.masbaha.GetMasbahaAzkarsUseCaseImpl
import com.der3.shared.domain.use_case.masbaha.SyncMasbahaDataUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MasbahaUseCasesTest {

    private val repository: MasbahaRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk(relaxed = true)

    @Test
    fun `GetMasbahaAzkarsUseCaseImpl returns local azkars flow`() = runTest {
        val useCase = GetMasbahaAzkarsUseCaseImpl(repository)
        val zekr = MasbahaAzkar(id = 1, text = "سبحان الله", count = 33)
        every { repository.getLocalAzkars() } returns flowOf(listOf(zekr))

        useCase.invoke().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("سبحان الله", list[0].text)
            awaitComplete()
        }
    }

    @Test
    fun `SyncMasbahaDataUseCaseImpl syncs remote azkars when remote version is newer`() = runTest {
        val useCase = SyncMasbahaDataUseCaseImpl(repository, dataStoreRepository)
        val remoteZekr = MasbahaAzkar(id = 1, text = "سبحان الله", count = 33)

        coEvery { repository.getRemoteVersion() } returns 2
        every { dataStoreRepository.masbahaDataVersion } returns 1
        every { repository.getLocalAzkars() } returns flowOf(emptyList())
        coEvery { repository.getRemoteAzkars() } returns listOf(remoteZekr)
        coEvery { repository.clearLocalAzkars() } returns Unit
        coEvery { repository.updateLocalAzkars(listOf(remoteZekr)) } returns Unit

        useCase.invoke().test {
            assertEquals(SyncState.SYNCING, awaitItem())
            assertEquals(SyncState.SUCCESS, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { repository.clearLocalAzkars() }
        coVerify(exactly = 1) { repository.updateLocalAzkars(listOf(remoteZekr)) }
    }
}
