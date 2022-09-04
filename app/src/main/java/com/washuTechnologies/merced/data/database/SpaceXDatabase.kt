package com.washuTechnologies.merced.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import com.washuTechnologies.merced.data.database.converters.StringArrayConverter
import com.washuTechnologies.merced.data.launches.datasources.LaunchesLocalDatasource

/**
 * Database for all locally stored SpaceX data.
 */
@Database(entities = [RocketLaunch::class], version = 1)
@TypeConverters(StringArrayConverter::class)
abstract class SpaceXDatabase : RoomDatabase() {
    /**
     * Data access object for information about rocket launches.
     */
    abstract fun launchDao(): LaunchesLocalDatasource
}
