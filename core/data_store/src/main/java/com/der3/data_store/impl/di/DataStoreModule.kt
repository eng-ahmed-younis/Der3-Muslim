package com.der3.data_store.impl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.der3.data_store.api.DataStoreRepository
import com.der3.data_store.impl.DataStoreRepositoryImpl
import com.der3.data_store.impl.DataStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "der3_data_store")

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideDataStoreRepo(
        dataStoreService: DataStoreService
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreService)
    }

}