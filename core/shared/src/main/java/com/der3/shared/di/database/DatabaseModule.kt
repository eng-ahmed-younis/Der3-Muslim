package com.der3.shared.di.database

import android.content.Context
import androidx.room.Room
import com.der3.shared.data.source.local.Der3MuslimDatabase
import com.der3.shared.data.source.local.dao.MasbahaAzkarDao
import com.der3.shared.data.source.local.dao.MasbahaHistoryDao
import com.der3.shared.data.source.local.dao.FavoritesDao
import com.der3.shared.data.source.local.dao.NotificationDao
import com.der3.shared.data.source.local.dao.RecycleBinDao
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
    fun provideDer3MuslimDatabase(
        @ApplicationContext context: Context
    ): Der3MuslimDatabase {
        return Room.databaseBuilder(
            context,
            Der3MuslimDatabase::class.java,
            DataBaseUtils.DATABASE_NAME
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    @Singleton
    fun provideMasbahaAzkarDao(database: Der3MuslimDatabase): MasbahaAzkarDao {
        return database.masbahaAzkarDao()
    }

    @Provides
    @Singleton
    fun provideMasbahaHistoryDao(database: Der3MuslimDatabase): MasbahaHistoryDao {
        return database.masbahaHistoryDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: Der3MuslimDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    @Singleton
    fun provideRecycleBinDao(database: Der3MuslimDatabase): RecycleBinDao {
        return database.recycleBinDao()
    }

    @Provides
    @Singleton
    fun provideNotificationDao(database: Der3MuslimDatabase): NotificationDao {
        return database.notificationDao()
    }
}
