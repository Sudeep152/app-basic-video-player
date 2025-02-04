package com.pubscale.basicvideoplayer.repository

import com.pubscale.basicvideoplayer.remote.model.VideoModelResponse
import com.pubscale.basicvideoplayer.utils.NetworkResult

interface BaseVideoPlayerRepository {
    suspend fun getVideoPlayerData(): NetworkResult<VideoModelResponse>
}