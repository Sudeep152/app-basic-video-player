package com.pubscale.basicvideoplayer.remote.api

import com.pubscale.basicvideoplayer.remote.model.VideoModelResponse
import retrofit2.http.GET

interface VideoApi {

    @GET("refs/heads/main/video_url.json")
    suspend fun getVideoUrl(): VideoModelResponse

}