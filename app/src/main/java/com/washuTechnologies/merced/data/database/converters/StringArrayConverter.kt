package com.washuTechnologies.merced.data.database.converters

import androidx.room.TypeConverter

/**
 * Converts Arrays of string to comma separated strings for storage in room and back again.
 */
class StringArrayConverter {
    /**
     *  Convert a string to an array separated by commas.
     */
    @TypeConverter
    fun stringToArray(input: String?): Array<String>? = input
        ?.split(",")
        ?.map { it.trim() }
        ?.toTypedArray()

    /**
     * Convert an array to a comma separated string.
     */
    @TypeConverter
    fun arrayToString(input: Array<String>?): String? = input?.joinToString(separator = ",")
}
