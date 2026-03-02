package com.der3.player.di

import com.der3.player.audio.api.AudioPlayer
import com.der3.player.audio.impl.AzkarAudioPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds [AzkarAudioPlayer] to [AudioPlayer] interface.
 * ViewModels depend on the interface, not the concrete class,
 * making it easy to swap implementations (e.g. ExoPlayer) later.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AudioBindingModule {

    @Binds
    @Singleton
    abstract fun bindAudioPlayer(
        impl: AzkarAudioPlayer
    ): AudioPlayer
}