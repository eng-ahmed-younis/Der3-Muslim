package com.der3.shared.di.use_case

import com.der3.shared.domain.use_case.GetMasbahaAzkarsUseCase
import com.der3.shared.domain.use_case.GetMasbahaAzkarsUseCaseImpl
import com.der3.shared.domain.use_case.SyncMasbahaDataUseCase
import com.der3.shared.domain.use_case.SyncMasbahaDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MasbahaUseCaseModule {

    @Binds
    abstract fun bindGetMasbahaAzkars(
        impl: GetMasbahaAzkarsUseCaseImpl
    ): GetMasbahaAzkarsUseCase

    @Binds
    abstract fun bindSyncMasbahaData(
        impl: SyncMasbahaDataUseCaseImpl
    ): SyncMasbahaDataUseCase
}