package com.der3.data.di

import android.content.Context
import com.der3.data.repo.AzkarRepositoryImpl
import com.der3.data.repo.api.AzkarRepository
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