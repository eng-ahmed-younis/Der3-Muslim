package com.der3.shared.data.repo

import app.cash.turbine.test
import com.der3.shared.data.dto.MasbahaAzkarDto
import com.der3.shared.data.source.MasbahaRemoteDataSource
import com.der3.shared.data.source.local.dao.MasbahaAzkarDao
import com.der3.shared.data.source.local.entity.MasbahaAzkarEntity
import com.der3.shared.domain.model.MasbahaAzkar
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MasbahaRepositoryImplTest {

    private val remoteDataSource: MasbahaRemoteDataSource = mockk()
    private val localDataSource: MasbahaAzkarDao = mockk()
    private lateinit var repository: MasbahaRepositoryImpl

    @Before
    fun setUp() {
        repository = MasbahaRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getRemoteVersion calls remoteDataSource getVersion`() = runTest {
        coEvery { remoteDataSource.getVersion() } returns 2

        val version = repository.getRemoteVersion()

        assertEquals(2, version)
        coVerify(exactly = 1) { remoteDataSource.getVersion() }
    }

    @Test
    fun `getRemoteAzkars converts DTOs to domain models`() = runTest {
        val dto = MasbahaAzkarDto(id = 1, text = "سبحان الله", count = 33)
        coEvery { remoteDataSource.getAzkars() } returns listOf(dto)

        val azkars = repository.getRemoteAzkars()

        assertEquals(1, azkars.size)
        assertEquals("سبحان الله", azkars[0].text)
    }

    @Test
    fun `getLocalAzkars converts entities to domain models`() = runTest {
        val entity = MasbahaAzkarEntity(id = 1, text = "الحمد لله", count = 33)
        every { localDataSource.getAllAzkars() } returns flowOf(listOf(entity))

        repository.getLocalAzkars().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("الحمد لله", list[0].text)
            awaitComplete()
        }
    }

    @Test
    fun `clearLocalAzkars calls deleteAllAzkars on localDataSource`() = runTest {
        coEvery { localDataSource.deleteAllAzkars() } returns Unit

        repository.clearLocalAzkars()

        coVerify(exactly = 1) { localDataSource.deleteAllAzkars() }
    }
}
