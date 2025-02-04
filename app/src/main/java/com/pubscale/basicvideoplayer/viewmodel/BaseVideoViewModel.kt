package com.pubscale.basicvideoplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubscale.basicvideoplayer.remote.model.VideoModelResponse
import com.pubscale.basicvideoplayer.repository.BaseVideoPlayerRepository
import com.pubscale.basicvideoplayer.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseVideoViewModel @Inject constructor(private val repository: BaseVideoPlayerRepository) :
    ViewModel() {
    // Fetching the VideoModelResponse from the repository and storing on live data
    private var _videoUrl = MutableLiveData<NetworkResult<VideoModelResponse>>()
    val videoUrl: MutableLiveData<NetworkResult<VideoModelResponse>> get() = _videoUrl


    // Initialize the ViewModel by fetching the video URL when created
    init {
        getVideoUrl()
    }

    // Function to fetch the video URL from the repository
    private fun getVideoUrl() {
        viewModelScope.launch {
            // Handle for loader
            _videoUrl.postValue(NetworkResult.Loading())
            // Fetch video player data from the repository and update live data
            val result = repository.getVideoPlayerData()
            _videoUrl.postValue(result)
        }
    }
}