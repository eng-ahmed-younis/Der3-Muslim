package com.der3.shared.data.repo

import app.cash.turbine.test
import com.der3.shared.data.source.local.dao.FavoritesDao
import com.der3.shared.data.source.local.entity.FavoriteEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryImplTest {

    private val favoritesDao: FavoritesDao = mockk()
    private lateinit var repository: FavoritesRepositoryImpl

    @Before
    fun setUp() {
        repository = FavoritesRepositoryImpl(favoritesDao)
    }

    @Test
    fun `getAllFavorites returns flow from dao`() = runTest {
        val favorite = FavoriteEntity(
            id = 1,
            categoryId = 10,
            text = "سبحان الله",
            audioPath = "path/audio.mp3",
            repeatCount = 3,
            categoryName = "أذكار الصباح"
        )
        every { favoritesDao.getAllFavorites() } returns flowOf(listOf(favorite))

        repository.getAllFavorites().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("سبحان الله", list[0].text)
            awaitComplete()
        }
    }

    @Test
    fun `addFavorite calls insertFavorite on dao`() = runTest {
        val favorite = FavoriteEntity(
            id = 1,
            categoryId = 10,
            text = "سبحان الله",
            audioPath = "path/audio.mp3",
            repeatCount = 3
        )
        coEvery { favoritesDao.insertFavorite(favorite) } returns Unit

        repository.addFavorite(favorite)

        coVerify(exactly = 1) { favoritesDao.insertFavorite(favorite) }
    }

    @Test
    fun `removeFavorite calls deleteFavoriteById on dao`() = runTest {
        coEvery { favoritesDao.deleteFavoriteById(1) } returns Unit

        repository.removeFavorite(1)

        coVerify(exactly = 1) { favoritesDao.deleteFavoriteById(1) }
    }

    @Test
    fun `isFavorite returns boolean flow from dao`() = runTest {
        every { favoritesDao.isFavorite(1) } returns flowOf(true)

        repository.isFavorite(1).test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }
}
