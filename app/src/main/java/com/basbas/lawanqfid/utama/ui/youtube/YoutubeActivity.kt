package com.basbas.lawanqfid.utama.ui.youtube

import android.content.Intent
import android.os.Bundle
import com.basbas.lawanqfid.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener
import kotlinx.android.synthetic.main.activity_youtube.*


class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    val YOUTUBE_API_KEY = "AIzaSyASxs8XEaG-cjcNu2Ubr75wb-5AxkPz27g"
    private var playerStateChangeListener: MyPlayerStateChangeListener? = null
    private var playbackEventListener: MyPlaybackEventListener? = null
    private var player: YouTubePlayer? = null
    private val RECOVERY_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        youtube_view.initialize(YOUTUBE_API_KEY,this@YoutubeActivity)
        playerStateChangeListener = MyPlayerStateChangeListener()
        playbackEventListener = MyPlaybackEventListener()

    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
     //   player = p1
        p1?.setPlayerStateChangeListener(playerStateChangeListener)
        p1?.setPlaybackEventListener(playbackEventListener);

        if (!p2) {
            p1?.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }


    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult?) {
        if (errorReason?.isUserRecoverableError()!!) {
            errorReason?.getErrorDialog(this, RECOVERY_REQUEST).show()
        } else {
//            val error = java.lang.String.format("Error"), errorReason.toString())
//            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider()?.initialize(YOUTUBE_API_KEY, this@YoutubeActivity);
        }
    }

    protected fun getYouTubePlayerProvider(): YouTubePlayer.Provider? {
        return youtube_view
    }

    private class MyPlaybackEventListener : PlaybackEventListener {
        override fun onPlaying() { // Called when playback starts, either due to user action or call to play().

        }

        override fun onPaused() { // Called when playback is paused, either due to user action or call to pause().

        }

        override fun onStopped() { // Called when playback stops for a reason other than being paused.

        }

        override fun onBuffering(b: Boolean) { // Called when buffering starts or ends.
        }

        override fun onSeekTo(i: Int) { // Called when a jump in playback position occurs, either
// due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private class MyPlayerStateChangeListener : PlayerStateChangeListener {
        override fun onLoading() { // Called when the player is loading a video
// At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        override fun onLoaded(s: String) { // Called when a video is done loading.
// Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        override fun onAdStarted() { // Called when playback of an advertisement starts.
        }

        override fun onVideoStarted() { // Called when playback of the video starts.
        }

        override fun onVideoEnded() { // Called when the video reaches its end.
        }

        override fun onError(errorReason: YouTubePlayer.ErrorReason) { // Called when an error occurs.
        }
    }
}
