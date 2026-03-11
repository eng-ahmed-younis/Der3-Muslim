package com.der3.shared.di

import android.content.Context
import androidx.room.Room
import com.der3.shared.data.source.local.MasbahaDatabase
import com.der3.shared.data.source.local.dao.MasbahaAzkarDao
import com.der3.shared.utils.DataBaseUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMasbahaDatabase(
        @ApplicationContext context: Context
    ): MasbahaDatabase {
        return Room.databaseBuilder(
            context,
            MasbahaDatabase::class.java,
            DataBaseUtils.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMasbahaAzkarDao(database: MasbahaDatabase): MasbahaAzkarDao {
        return database.masbahaAzkarDao()
    }
}