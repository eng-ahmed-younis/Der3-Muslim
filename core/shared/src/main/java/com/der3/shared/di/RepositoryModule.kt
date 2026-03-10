package com.der3.shared.di

import android.content.Context
import com.der3.shared.data.repo.AzkarRepositoryImpl
import com.der3.shared.domain.repo.AzkarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAzkarRepository(
        @ApplicationContext context: Context
    ): AzkarRepository = AzkarRepositoryImpl(context)
}