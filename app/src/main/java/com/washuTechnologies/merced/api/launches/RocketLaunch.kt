package com.washuTechnologies.merced.api.launches

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model object representing a spaceX rocket launch.
 */
@JsonClass(generateAdapter = true)
data class RocketLaunch(
    @Json(name = "flight_number")
    val flightNumber: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "date_utc")
    val dateUTC: String
)
