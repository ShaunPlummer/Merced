package com.washuTechnologies.merced.di

import android.content.Context
import androidx.room.Room
import com.washuTechnologies.merced.data.database.SpaceXDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)
object RoomTestingModule {

    @Provides
    fun launchesDatabase(@ApplicationContext applicationContext: Context): SpaceXDatabase =
        Room.inMemoryDatabaseBuilder(
            applicationContext,
            SpaceXDatabase::class.java
        ).allowMainThreadQueries()
            .build()

    @Provides
    fun launchesDao(database: SpaceXDatabase) = database.launchDao()
}
