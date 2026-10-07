package com.der3.shared.domain.use_case

import app.cash.turbine.test
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.repo.FavoritesRepository
import com.der3.shared.domain.use_case.fav.AddToFavoriteUseCase
import com.der3.shared.domain.use_case.fav.GetAllFavouritesUsecase
import com.der3.shared.domain.use_case.fav.IsFavoriteUseCase
import com.der3.shared.domain.use_case.fav.RemoveFromFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoritesUseCasesTest {

    private val repository: FavoritesRepository = mockk()

    @Test
    fun `AddToFavoriteUseCase invokes addFavorite on repository`() = runTest {
        val useCase = AddToFavoriteUseCase(repository)
        val favorite = FavoriteEntity(id = 1, categoryId = 1, text = "ذِكر", audioPath = "", repeatCount = 1)
        coEvery { repository.addFavorite(favorite) } returns Unit

        useCase.invoke(favorite)

        coVerify(exactly = 1) { repository.addFavorite(favorite) }
    }

    @Test
    fun `RemoveFromFavoriteUseCase invokes removeFavorite on repository`() = runTest {
        val useCase = RemoveFromFavoriteUseCase(repository)
        coEvery { repository.removeFavorite(1) } returns Unit

        useCase.invoke(1)

        coVerify(exactly = 1) { repository.removeFavorite(1) }
    }

    @Test
    fun `IsFavoriteUseCase invokes isFavorite on repository`() = runTest {
        val useCase = IsFavoriteUseCase(repository)
        every { repository.isFavorite(1) } returns flowOf(true)

        useCase.invoke(1).test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `GetAllFavouritesUsecase invokes getAllFavorites on repository`() = runTest {
        val useCase = GetAllFavouritesUsecase(repository)
        val favorite = FavoriteEntity(id = 1, categoryId = 1, text = "ذِكر", audioPath = "", repeatCount = 1)
        every { repository.getAllFavorites() } returns flowOf(listOf(favorite))

        useCase.invoke().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            awaitComplete()
        }
    }
}
