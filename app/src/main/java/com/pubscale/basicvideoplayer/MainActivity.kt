package com.pubscale.basicvideoplayer

import android.app.PictureInPictureParams
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.pubscale.basicvideoplayer.databinding.ActivityMainBinding
import com.pubscale.basicvideoplayer.utils.NetworkResult
import com.pubscale.basicvideoplayer.viewmodel.BaseVideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var player: ExoPlayer? = null

    private val mViewModel by viewModels<BaseVideoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeVideoUrl()
        handleBackPress()
    }

    // This function subscribes to video URL updates.
    private fun subscribeVideoUrl() {
        binding.apply {
            // The code here subscribes to changes in the video URL.
            mViewModel.videoUrl.observe(this@MainActivity) {
                when (it) {
                    is NetworkResult.Loading -> {
                        pbView.visibility = View.VISIBLE
                        lottieView.visibility = View.GONE
                    }

                    is NetworkResult.Success -> {
                        pbView.visibility = View.GONE
                        playerView.visibility = View.VISIBLE
                        lottieView.visibility = View.GONE
                        setupExoPlayer(it.data?.url ?: "")
                    }

                    is NetworkResult.Error -> {
                        pbView.visibility = View.GONE
                        playerView.visibility = View.VISIBLE
                        lottieView.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // This function sets up the ExoPlayer with a given URL.
    private fun setupExoPlayer(url: String) {
        if (url.isNotEmpty() && Uri.parse(url).scheme != null) {
            player = ExoPlayer.Builder(this).build()
            binding.playerView.player = player
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.playWhenReady = true
        } else {
            Toast.makeText(this, "video URL not valid", Toast.LENGTH_SHORT).show()
        }
    }

    // This function enters Picture-In-Picture mode if the OS version supports it.
    private fun enterPiPMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(16, 9))
                .build()
            enterPictureInPictureMode(params)
        }
    }

    // This function handles the back press event.
    private fun handleBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    enterPiPMode()
                } else {
                    finish()
                }
            }
        })
    }

    // This method is called when the user navigates away from the activity such as pressing the home button or switching to another app.
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        enterPiPMode()
    }

    override fun onRestart() {
        super.onRestart()
        player?.play()
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }
}