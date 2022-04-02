package com.washuTechnologies.merced.api.launches

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model object representing a spaceX rocket launch.
 */
@JsonClass(generateAdapter = true)
data class RocketLaunch(
    @Json(name = "id")
    val id: String,
    @Json(name = "flight_number")
    val flightNumber: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "date_utc")
    val dateUTC: String,
    @Json(name = "details")
    val details: String? = null,
    @Json(name = "links")
    val links: Links? = null
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
