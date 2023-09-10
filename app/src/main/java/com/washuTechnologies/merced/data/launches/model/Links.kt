package com.washuTechnologies.merced.data.launches.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Links to additional resources and information about rocket launches.
 */
@Entity
@JsonClass(generateAdapter = true)
data class Links(
    @Embedded
    @Json(name = "patch")
    val patch: Patch?,
    @Json(name = "flickr")
    @Embedded
    val flickr: Flickr?,
    @Json(name = "presskit")
    val presskit: String?,
    @Json(name = "webcast")
    val webcast: String?,
    @Json(name = "youtubeId")
    val youtubeId: String?,
    @Json(name = "article")
    val article: String?,
    @Json(name = "wikipedia")
    val wikipedia: String?,
    @Json(name = "reddit")
    @Embedded
    val reddit: Reddit?,
    @Json(name = "youtube_id")
    val videoLink: String?
)

/**
 * Links to Flickr resource about the rocket launch.
 */
@Entity
@JsonClass(generateAdapter = true)
data class Flickr (
    @ColumnInfo(name = "flicker_small")
    @Json(name = "small")
    val small: Array<String>?,
    @Json(name = "original")
    val original: Array<String>?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Flickr

        if (small != null) {
            if (other.small == null) return false
            if (!small.contentEquals(other.small)) return false
        } else if (other.small != null) return false
        if (original != null) {
            if (other.original == null) return false
            if (!original.contentEquals(other.original)) return false
        } else if (other.original != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = small?.contentHashCode() ?: 0
        result = 31 * result + (original?.contentHashCode() ?: 0)
        return result
    }
}

/**
 * Links to images of the mission patch.
 */
@Entity
@JsonClass(generateAdapter = true)
data class Patch(
    @ColumnInfo(name = "patch_small")
    @Json(name = "small")
    val small: String?,
    @ColumnInfo(name = "patch_large")
    @Json(name = "large")
    val large: String?
)

/**
 * Links to reddit posts about the rocket launch.
 */
@Entity
@JsonClass(generateAdapter = true)
data class Reddit(
    @Json(name = "campaign")
    val campaign: String?,
    @Json(name = "launch")
    val launch: String?,
    @Json(name = "recovery")
    val recovery: String?,
    @Json(name = "media")
    val media: String?
)