package com.der3.muslims

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.AppStyle
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val context: Context = mockk(relaxed = true)
    private val dataStoreRepository: DataStoreRepository = mockk(relaxed = true)
    private val resources: Resources = mockk(relaxed = true)
    private val configuration: Configuration = Configuration()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { context.resources } returns resources
        every { resources.configuration } returns configuration
        every { dataStoreRepository.appStyleFlow } returns flowOf(AppStyle.LIGHT.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observes appStyleFlow and updates appStyleFlow state`() = runTest {
        viewModel = MainViewModel(context, dataStoreRepository)
        advanceUntilIdle()

        assertEquals(AppStyle.LIGHT, viewModel.appStyleFlow.value)
    }

    @Test
    fun `getSystemTheme returns theme from dataStoreRepository`() {
        every { dataStoreRepository.appStyle } returns AppStyle.DARK.value

        viewModel = MainViewModel(context, dataStoreRepository)

        assertEquals(AppStyle.DARK, viewModel.getSystemTheme())
    }
}
