package com.der3.home.di.use_case

import com.der3.home.domain.use_case.ObserveAzkarAudioStateUseCase
import com.der3.home.domain.use_case.ObserveAzkarAudioStateUseCaseImpl
import com.der3.home.domain.use_case.PauseAzkarAudioUseCase
import com.der3.home.domain.use_case.PauseAzkarAudioUseCaseImpl
import com.der3.home.domain.use_case.PlayAzkarAudioUseCase
import com.der3.home.domain.use_case.PlayAzkarAudioUseCaseImpl
import com.der3.home.domain.use_case.ReleaseAzkarAudioUseCase
import com.der3.home.domain.use_case.ReleaseAzkarAudioUseCaseImpl
import com.der3.home.domain.use_case.ResumeAzkarAudioUseCase
import com.der3.home.domain.use_case.ResumeAzkarAudioUseCaseImpl
import com.der3.home.domain.use_case.StopAzkarAudioUseCase
import com.der3.home.domain.use_case.StopAzkarAudioUseCaseImpl
import com.der3.home.domain.use_case.ToggleAzkarAudioUseCase
import com.der3.home.domain.use_case.ToggleAzkarAudioUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds all audio use case interfaces to their implementations.
 * All scoped as Singleton to share state across the app.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AudioUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindPlayAzkarAudio(
        impl: PlayAzkarAudioUseCaseImpl
    ): PlayAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindPauseAzkarAudio(
        impl: PauseAzkarAudioUseCaseImpl
    ): PauseAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindResumeAzkarAudio(
        impl: ResumeAzkarAudioUseCaseImpl
    ): ResumeAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindStopAzkarAudio(
        impl: StopAzkarAudioUseCaseImpl
    ): StopAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindToggleAzkarAudio(
        impl: ToggleAzkarAudioUseCaseImpl
    ): ToggleAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindReleaseAzkarAudio(
        impl: ReleaseAzkarAudioUseCaseImpl
    ): ReleaseAzkarAudioUseCase

    @Binds
    @Singleton
    abstract fun bindObserveAzkarAudioState(
        impl: ObserveAzkarAudioStateUseCaseImpl
    ): ObserveAzkarAudioStateUseCase
}