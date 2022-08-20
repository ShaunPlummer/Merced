package com.washuTechnologies.merced.di

import android.content.Context
import androidx.room.Room
import com.washuTechnologies.merced.database.SpaceXDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun launchesDatabase(@ApplicationContext applicationContext: Context): SpaceXDatabase =
        Room.databaseBuilder(
            applicationContext,
            SpaceXDatabase::class.java, "launches-database"
        ).build()

    @Provides
    fun launchesDao(database: SpaceXDatabase) = database.launchDao()
}