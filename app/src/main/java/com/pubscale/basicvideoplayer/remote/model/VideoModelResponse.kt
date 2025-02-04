package com.pubscale.basicvideoplayer.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoModelResponse(
    @Json(name = "url")
    val url: String
)
