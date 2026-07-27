package com.dunihuliapps.myglidingassistant.domain.di

import android.content.Context
import androidx.room.Room
import com.dunihuliapps.myglidingassistant.domain.db.AppDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        )
        .addMigrations(AppDatabase.MIGRATION_2_3, AppDatabase.MIGRATION_3_4)
        .build()
    }

    @Provides
    @Singleton
    fun provideFlightDao(database: AppDatabase) = database.flightDao()

    @Provides
    @Singleton
    fun provideGliderDao(database: AppDatabase) = database.glidersDao()

    @Provides
    @Singleton
    fun provideAirfieldsDao(database: AppDatabase) = database.airfieldsDao()
}