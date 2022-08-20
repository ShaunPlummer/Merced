package com.washuTechnologies.merced.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.database.converters.StringArrayConverter
import com.washuTechnologies.merced.database.launches.LaunchesLocalDatasource

@Database(entities = [RocketLaunch::class], version = 1)
@TypeConverters(StringArrayConverter::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchesLocalDatasource
}
