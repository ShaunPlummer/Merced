package com.washuTechnologies.merced.api.launches

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "patch")
    val patch: Patch? = Patch(),
    @Json(name = "flickr")
    val flickr: Flickr? = Flickr(),
    @Json(name = "presskit")
    val presskit: String? = null,
    @Json(name = "webcast")
    val webcast: String? = null,
    @Json(name = "youtubeId")
    val youtubeId: String? = null,
    @Json(name = "article")
    val article: String? = null,
    @Json(name = "wikipedia")
    val wikipedia: String? = null,
    @Json(name = "reddit")
    val reddit: Reddit? = null,
    @Json(name = "youtube_id")
    val videoLink: String? = null
)

@JsonClass(generateAdapter = true)
data class Flickr(
    @Json(name = "small")
    val small: Array<String>? = null,
    @Json(name = "original")
    val original: Array<String>? = null,
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

@JsonClass(generateAdapter = true)
data class Patch(
    @Json(name = "small")
    val small: String? = null,
    @Json(name = "large")
    val large: String? = null
)

@JsonClass(generateAdapter = true)
data class Reddit(
    @Json(name = "campaign")
    val campaign: String? = null,
    @Json(name = "launch")
    val launch: String? = null,
    @Json(name = "recovery")
    val recovery: String? = null,

    @Json(name = "media")
    val media: String? = null
)