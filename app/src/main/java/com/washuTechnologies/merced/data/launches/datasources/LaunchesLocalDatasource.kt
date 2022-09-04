package com.washuTechnologies.merced.data.launches.datasources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for all locally stored information about rocket launches.
 */
@Dao
interface LaunchesLocalDatasource {
    /**
     * Retrieve a list of all know rocket launches.
     */
    @Query("SELECT * FROM rocketlaunch")
    fun getAll(): Flow<Array<RocketLaunch>>

    /**
     * Retrieve a specific rocket launch using its [launchId].
     */
    @Query("SELECT * FROM rocketlaunch WHERE id LIKE :launchId")
    fun getLaunch(launchId: String): Flow<RocketLaunch?>

    /**
     * Returns true if the local data source contains any information about a rocket launch with a given [launchId].
     */
    @Query("SELECT COUNT(*) FROM rocketlaunch WHERE id LIKE :launchId")
    fun hasLaunch(launchId: String): Boolean

    /**
     * Store the [launchList] in local storage.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(launchList: Array<RocketLaunch>): Array<Long>

    /**
     * Store the [rocket] launch information locally.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rocket: RocketLaunch): Long
}