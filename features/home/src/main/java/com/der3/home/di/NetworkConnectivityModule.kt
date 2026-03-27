package com.der3.home.di

import android.content.Context
import com.der3.utils.connectivity.ConnectivityObserver
import com.der3.utils.connectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkConnectivityModule {


    @Singleton
    @Provides
    fun provideConnectivityObserver(
       @ApplicationContext context: Context
    ): ConnectivityObserver = NetworkConnectivityObserver(context = context)
}