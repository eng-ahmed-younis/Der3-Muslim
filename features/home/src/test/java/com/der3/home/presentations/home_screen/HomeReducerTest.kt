package com.der3.home.presentations.home_screen

import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.ui.models.CategoryUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeReducerTest {

    private val reducer = HomeReducer()

    @Test
    fun `OnLoading action updates isLoading in state`() {
        val initialState = HomeState(isLoading = false)
        val newState = reducer.reduce(HomeAction.OnLoading(true), initialState)

        assertTrue(newState.isLoading)
    }

    @Test
    fun `LoadHomeAzkarCategory action updates categories list in state`() {
        val initialState = HomeState()
        val categories = listOf(
            CategoryUi(id = 1, title = "أذكار الصباح", subtitle = "صبح", count = "10")
        )
        val newState = reducer.reduce(HomeAction.LoadHomeAzkarCategory(categories), initialState)

        assertEquals(1, newState.homeAzkarCategory.size)
        assertEquals("أذكار الصباح", newState.homeAzkarCategory[0].title)
    }

    @Test
    fun `LoadDailyNotification action updates title and desc in state`() {
        val initialState = HomeState()
        val newState = reducer.reduce(
            HomeAction.LoadDailyNotification(title = "تنبيه يومي", desc = "محتوى التنبيه"),
            initialState
        )

        assertEquals("تنبيه يومي", newState.dailyNotificationTitle)
        assertEquals("محتوى التنبيه", newState.dailyNotificationDesc)
    }

    @Test
    fun `LoadUnreadNotificationCount action updates count in state`() {
        val initialState = HomeState(unreadNotificationCount = 0)
        val newState = reducer.reduce(HomeAction.LoadUnreadNotificationCount(count = 5), initialState)

        assertEquals(5, newState.unreadNotificationCount)
    }

    @Test
    fun `ClearError action clears error in state`() {
        val initialState = HomeState(error = null)
        val newState = reducer.reduce(HomeAction.ClearError, initialState)

        assertNull(newState.error)
    }
}
