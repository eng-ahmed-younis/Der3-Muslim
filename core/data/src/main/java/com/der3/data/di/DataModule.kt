package com.der3.data.di

import android.content.Context
import com.der3.data.repo.AzkarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAzkarRepository(
        @ApplicationContext context: Context
    ): AzkarRepository = AzkarRepository(context)
}