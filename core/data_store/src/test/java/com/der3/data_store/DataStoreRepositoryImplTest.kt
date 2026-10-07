package com.der3.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.der3.data_store.impl.DataStoreKeys
import com.der3.data_store.impl.DataStoreRepositoryImpl
import com.der3.data_store.impl.DataStoreService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DataStoreRepositoryImplTest {

    private val preferences: Preferences = mockk()
    private val dataStore: DataStore<Preferences> = mockk()
    private lateinit var dataStoreService: DataStoreService
    private lateinit var repository: DataStoreRepositoryImpl

    @Before
    fun setUp() {
        every { dataStore.data } returns flowOf(preferences)
        dataStoreService = DataStoreService(dataStore)
        repository = DataStoreRepositoryImpl(dataStoreService)
    }

    @Test
    fun `get hasCompletedOnboarding reads from dataStoreService`() {
        val key = booleanPreferencesKey(DataStoreKeys.ONBOARDING_KEY)
        every { preferences[key] } returns true

        val completed = repository.hasCompletedOnboarding

        assertTrue(completed)
    }

    @Test
    fun `set hasCompletedOnboarding calls dataStoreService set`() {
        coEvery { dataStore.updateData(any()) } returns preferences

        repository.hasCompletedOnboarding = true

        coVerify { dataStore.updateData(any()) }
    }

    @Test
    fun `get and set zekrScreenDetailsFontSize`() {
        val key = intPreferencesKey(DataStoreKeys.ZEKR_DETAILS_SCREEN_FONT_SIZE)
        every { preferences[key] } returns 28
        coEvery { dataStore.updateData(any()) } returns preferences

        assertEquals(28, repository.zekrScreenDetailsFontSize)

        repository.zekrScreenDetailsFontSize = 28
        coVerify { dataStore.updateData(any()) }
    }

    @Test
    fun `get and set masbahaDataVersion`() {
        val key = intPreferencesKey(DataStoreKeys.MASBAHA_DATA_VERSION)
        every { preferences[key] } returns 5
        coEvery { dataStore.updateData(any()) } returns preferences

        assertEquals(5, repository.masbahaDataVersion)

        repository.masbahaDataVersion = 5
        coVerify { dataStore.updateData(any()) }
    }
}
