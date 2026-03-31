package com.der3.player.di

import android.content.Context
import com.der3.data_store.api.DataStoreRepository
import com.der3.player.audio.impl.AzkarAudioPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides [AzkarAudioPlayer] instance to the DI graph.
 * Installed in [SingletonComponent] so one instance is shared app-wide.
 */
@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    /**
     * Provides the concrete [AzkarAudioPlayer] with application context.
     * Using @Provides here instead of @Binds because we need to pass context.
     */
    @Provides
    @Singleton
    fun provideAzkarAudioPlayer(
        @ApplicationContext context: Context,
        dataStoreRepository: DataStoreRepository
    ): AzkarAudioPlayer = AzkarAudioPlayer(
        dataStoreRepository = dataStoreRepository,
        context = context
    )
}

