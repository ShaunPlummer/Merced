package com.washuTechnologies.merced.data.launches.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.washuTechnologies.merced.data.launches.model.RocketLaunch

@Dao
interface LaunchesLocalDatasource {
    @Query("SELECT * FROM rocketlaunch")
    fun getAll(): Array<RocketLaunch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launchList: Array<RocketLaunch>): Array<Long>
}