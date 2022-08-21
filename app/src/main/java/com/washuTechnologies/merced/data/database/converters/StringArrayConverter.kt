package com.washuTechnologies.merced.data.database.converters

import androidx.room.TypeConverter

/**
 * Converts Arrays of string to comma separated strings for storage in room and back again.
 */
class StringArrayConverter {
    @TypeConverter
    fun stringToArray(input: String?): Array<String>? = input
        ?.split(",")
        ?.map { it.trim() }
        ?.toTypedArray()

    @TypeConverter
    fun arrayToString(input: Array<String>?): String? = input?.joinToString(separator = ",")
}
