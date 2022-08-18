package com.washuTechnologies.merced.database.launches

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.washuTechnologies.merced.api.launches.RocketLaunch

@Dao
interface LaunchesLocalDatasource {
    @Query("SELECT * FROM rocketlaunch")
    fun getAll(): Array<RocketLaunch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launchList: Array<RocketLaunch>): Array<Long>
}