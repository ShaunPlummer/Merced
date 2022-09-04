package com.washuTechnologies.merced.util

import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Helpers for working with date information about rocket launches.
 */
object DateHelper {

    private fun parseUtcLaunchDate(utcDate: String): Date? {
        val sdf = SimpleDateFormat(RocketLaunch.UTC_FORMAT, Locale.UK)
        return sdf.parse(utcDate)
    }

    private fun formatDate(date: Date): String {
        val df = SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault())
        return df.format(date)
    }

    /**
     * Convert a UTC date to a display ready format. If the date cannot be parsed the raw value
     * is returned.
     */
    fun utcLaunchDateToDisplay(utcDate: String) =
        parseUtcLaunchDate(utcDate)?.let { formatDate(it) } ?: utcDate
}
