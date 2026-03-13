package com.der3.sections.di

import com.der3.sections.data.service.api.PrayerTimeService
import com.der3.sections.data.service.impl.PrayerTimeServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrayerTimeModule {

    @Provides
    @Singleton
    fun providePrayerTimeService(
        client: HttpClient,
    ): PrayerTimeService {
        return PrayerTimeServiceImpl(client)
    }
}