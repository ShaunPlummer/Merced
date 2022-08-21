package com.washuTechnologies.merced.data.launches.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model object representing a spaceX rocket launch.
 */
@Entity
@JsonClass(generateAdapter = true)
data class RocketLaunch(
    @PrimaryKey
    @Json(name = "id")
    val id: String,
    @Json(name = "flight_number")
    val flightNumber: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "date_utc")
    val dateUTC: String,
    @Json(name = "static_fire_date_utc")
    val staticFireDateUTC: String?,
    @Json(name = "details")
    val details: String? = null,
    @Embedded
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "launchpad")
    val launchpad: String? = null
) {

    /**
     * Retrieves the first image of the launch if any are present.
     */
    fun findImage(): String? =
        links?.flickr?.small?.firstOrNull() ?: links?.flickr?.original?.firstOrNull()

    companion object {
        /**
         * UTC launch date/time in ISO 8601 format
         */
        const val UTC_FORMAT = "yyyyy-MM-dd'T'HH:mm:ss.SSS"
    }
}
