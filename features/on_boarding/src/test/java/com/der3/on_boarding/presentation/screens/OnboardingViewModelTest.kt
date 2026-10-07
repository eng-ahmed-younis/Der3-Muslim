package com.der3.on_boarding.presentation.screens

import app.cash.turbine.test
import com.der3.data_store.api.DataStoreRepository
import com.der3.mvi.MviEffect
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingIntent
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class OnboardingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val dataStoreRepository: DataStoreRepository = mockk(relaxed = true)
    private lateinit var viewModel: OnboardingViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel(dataStoreRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `CompleteOnBoarding intent saves state and emits navigate effect`() = runTest {
        viewModel.effects.test {
            viewModel.onIntent(OnBoardingIntent.CompleteOnBoarding(page = 2))
            advanceUntilIdle()

            coVerify { dataStoreRepository.hasCompletedOnboarding = true }
            val effect = awaitItem()
            assertTrue(effect is MviEffect.Navigate)
        }
    }

    @Test
    fun `SkipOnBoarding intent saves state and emits navigate effect`() = runTest {
        viewModel.effects.test {
            viewModel.onIntent(OnBoardingIntent.SkipOnBoarding(page = 0))
            advanceUntilIdle()

            coVerify { dataStoreRepository.hasCompletedOnboarding = true }
            val effect = awaitItem()
            assertTrue(effect is MviEffect.Navigate)
        }
    }
}
