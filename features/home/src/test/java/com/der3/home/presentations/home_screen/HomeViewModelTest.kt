package com.der3.home.presentations.home_screen

import app.cash.turbine.test
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.mvi.MviEffect
import com.der3.shared.data.source.local.entity.NotificationEntity
import com.der3.shared.domain.model.AzkarCategory
import com.der3.shared.domain.use_case.GetAzkarCategoriesUseCase
import com.der3.shared.domain.use_case.notification.GetNotificationByTypeUseCase
import com.der3.shared.domain.use_case.notification.InsertNotificationUseCase
import com.der3.shared.domain.use_case.notification.read_status.GetUnReadNotificationCountUseCase
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getAllCategoriesUseCase: GetAzkarCategoriesUseCase = mockk()
    private val getNotificationByTypeUseCase: GetNotificationByTypeUseCase = mockk()
    private val getUnReadNotificationCountUseCase: GetUnReadNotificationCountUseCase = mockk()
    private val insertNotificationUseCase: InsertNotificationUseCase = mockk()
    private val reducer = HomeReducer()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val category = AzkarCategory(id = 1, category = "أذكار الصباح", audio = "", filename = "", items = emptyList())
        val notification = NotificationEntity(id = "1", title = "تنبيه", body = "محتوى")

        every { getAllCategoriesUseCase.invoke() } returns flowOf(listOf(category))
        every { getNotificationByTypeUseCase.invoke(any()) } returns flowOf(notification)
        every { getUnReadNotificationCountUseCase.invoke() } returns flowOf(3)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads categories, daily notification, and unread count`() = runTest {
        viewModel = HomeViewModel(
            getAllCategoriesUseCase,
            getNotificationByTypeUseCase,
            getUnReadNotificationCountUseCase,
            insertNotificationUseCase,
            reducer
        )
        advanceUntilIdle()

        assertEquals(1, viewModel.viewState.homeAzkarCategory.size)
        assertEquals("تنبيه", viewModel.viewState.dailyNotificationTitle)
        assertEquals(3, viewModel.viewState.unreadNotificationCount)
    }

    @Test
    fun `NavigateToAllCategories intent emits Navigate effect`() = runTest {
        viewModel = HomeViewModel(
            getAllCategoriesUseCase,
            getNotificationByTypeUseCase,
            getUnReadNotificationCountUseCase,
            insertNotificationUseCase,
            reducer
        )
        advanceUntilIdle()

        viewModel.effects.test {
            viewModel.onIntent(HomeIntent.NavigateToAllCategories)
            advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is MviEffect.Navigate)
        }
    }
}
