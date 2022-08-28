package com.washuTechnologies.merced.data.launches.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchesLocalDatasource {
    @Query("SELECT * FROM rocketlaunch")
    fun getAll(): Flow<Array<RocketLaunch>>

    @Query("SELECT * FROM rocketlaunch WHERE id LIKE :searchId")
    fun getLaunch(searchId: String): Flow<RocketLaunch?>

    @Query("SELECT COUNT(*) FROM rocketlaunch WHERE id LIKE :searchId")
    fun hasLaunch(searchId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launchList: Array<RocketLaunch>): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launchList: RocketLaunch): Long
}