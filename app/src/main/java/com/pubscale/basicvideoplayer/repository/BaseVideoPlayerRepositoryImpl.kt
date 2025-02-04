package com.pubscale.basicvideoplayer.repository

import com.pubscale.basicvideoplayer.remote.api.VideoApi
import com.pubscale.basicvideoplayer.remote.model.VideoModelResponse
import com.pubscale.basicvideoplayer.utils.NetworkResult
import java.lang.Exception
import javax.inject.Inject

class BaseVideoPlayerRepositoryImpl @Inject constructor(private val api: VideoApi) :
    BaseVideoPlayerRepository {
    override suspend fun getVideoPlayerData(): NetworkResult<VideoModelResponse> {
        // Attempt to fetch the video URL from the API
        return try {
            val response = api.getVideoUrl()
            // Return successful result in generic sealed class
            NetworkResult.Success(response)
        } catch (e: Exception) {
            // Return error result with an error message if an exception occurs
            NetworkResult.Error("Error occurred: Invalid responses.")
        }
    }
}