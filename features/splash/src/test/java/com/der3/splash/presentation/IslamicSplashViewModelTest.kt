package com.der3.splash.presentation

import app.cash.turbine.test
import com.der3.data_store.api.DataStoreRepository
import com.der3.mvi.MviEffect
import com.der3.shared.domain.repo.AzkarRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IslamicSplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val dataStoreRepository: DataStoreRepository = mockk()
    private val azkarRepository: AzkarRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { azkarRepository.getAllCategories() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `navigates to Home when onboarding is completed`() = runTest {
        every { dataStoreRepository.hasCompletedOnboarding } returns true

        val viewModel = IslamicSplashViewModel(dataStoreRepository, azkarRepository)

        viewModel.effects.test {
            advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is MviEffect.Navigate)
        }
    }

    @Test
    fun `navigates to Onboarding when onboarding is not completed`() = runTest {
        every { dataStoreRepository.hasCompletedOnboarding } returns false

        val viewModel = IslamicSplashViewModel(dataStoreRepository, azkarRepository)

        viewModel.effects.test {
            advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is MviEffect.Navigate)
        }
    }
}
