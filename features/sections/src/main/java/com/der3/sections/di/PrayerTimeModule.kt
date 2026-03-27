package com.der3.sections.di

import com.der3.sections.data.repository.PrayerRepositoryImpl
import com.der3.sections.data.service.api.PrayerTimeService
import com.der3.sections.data.service.impl.PrayerTimeServiceImpl
import com.der3.sections.domain.repository.IPrayerRepository
import com.der3.sections.domain.use_case.prayer.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerTimeModule {

    @Binds
    @Singleton
    abstract fun bindPrayerRepository(
        prayerRepositoryImpl: PrayerRepositoryImpl
    ): IPrayerRepository

    @Binds
    @Singleton
    abstract fun bindGetCombinedPrayerInfoUseCase(
        impl: GetCombinedPrayerInfoUseCaseImpl
    ): GetCombinedPrayerInfoUseCase

    @Binds
    @Singleton
    abstract fun bindGetNextPrayerUseCase(
        impl: GetNextPrayerUseCaseImpl
    ): GetNextPrayerUseCase

    @Binds
    @Singleton
    abstract fun bindGetAllPrayersWithStatusUseCase(
        impl: GetAllPrayersWithStatusUseCaseImpl
    ): GetAllPrayersWithStatusUseCase

    companion object {
        @Provides
        @Singleton
        fun providePrayerTimeService(
            client: HttpClient,
        ): PrayerTimeService {
            return PrayerTimeServiceImpl(client)
        }
    }
}
