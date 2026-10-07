package com.der3.shared.data.repo

import app.cash.turbine.test
import com.der3.shared.data.source.local.dao.FavoritesDao
import com.der3.shared.data.source.local.dao.RecycleBinDao
import com.der3.shared.data.source.local.entity.RecycleBinEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecycleBinRepositoryImplTest {

    private val recycleBinDao: RecycleBinDao = mockk()
    private val favoritesDao: FavoritesDao = mockk()
    private lateinit var repository: RecycleBinRepositoryImpl

    @Before
    fun setUp() {
        repository = RecycleBinRepositoryImpl(recycleBinDao, favoritesDao)
    }

    @Test
    fun `getAllDeletedItems returns flow from dao`() = runTest {
        val item = RecycleBinEntity(
            id = 1,
            categoryId = 2,
            text = "ذِكر محذوف",
            audioPath = "path/audio.mp3",
            repeatCount = 1,
            deletedAt = System.currentTimeMillis()
        )
        every { recycleBinDao.getAllDeletedItems() } returns flowOf(listOf(item))

        repository.getAllDeletedItems().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("ذِكر محذوف", list[0].text)
            awaitComplete()
        }
    }

    @Test
    fun `clearRecycleBin calls clearRecycleBin on dao`() = runTest {
        coEvery { recycleBinDao.clearRecycleBin() } returns Unit

        repository.clearRecycleBin()

        coVerify(exactly = 1) { recycleBinDao.clearRecycleBin() }
    }
}
