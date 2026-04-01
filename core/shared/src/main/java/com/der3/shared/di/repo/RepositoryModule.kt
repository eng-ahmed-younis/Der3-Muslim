package com.der3.shared.di.repo

import android.content.Context
import com.der3.shared.data.repo.AzkarRepositoryImpl
import com.der3.shared.data.repo.FavoritesRepositoryImpl
import com.der3.shared.data.repo.MasbahaRepositoryImpl
import com.der3.shared.data.repo.NotificationRepositoryImpl
import com.der3.shared.data.repo.RecycleBinRepositoryImpl
import com.der3.shared.domain.repo.AzkarRepository
import com.der3.shared.domain.repo.FavoritesRepository
import com.der3.shared.domain.repo.MasbahaRepository
import com.der3.shared.domain.repo.NotificationRepository
import com.der3.shared.domain.repo.RecycleBinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMasbahaRepository(
        impl: MasbahaRepositoryImpl
    ): MasbahaRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindRecycleBinRepository(
        impl: RecycleBinRepositoryImpl
    ): RecycleBinRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

    companion object {
        @Provides
        @Singleton
        fun provideAzkarRepository(
            @ApplicationContext context: Context
        ): AzkarRepository = AzkarRepositoryImpl(context)
    }
}