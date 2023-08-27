package com.moviestreaming.ui.trailer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.moviestreaming.databinding.FragmentTrailerBinding
import com.moviestreaming.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TrailerFragment : Fragment() {

    private var _binding: FragmentTrailerBinding? = null
    private val binding get() = _binding!!
    private val args: TrailerFragmentArgs by navArgs()
    private lateinit var simpleExoplayer: SimpleExoPlayer

    private var player: ExoPlayer? = null
    private val isPlaying get() = player?.isPlaying ?: false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTrailerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
//        releasePlayer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build()

        val url = Constants.YOUTUBE_URL + args.movieKey
        Log.d("YYYYYYYY", "initializePlayer: $url")

        // create a media item.
        val mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        // Create a media source and pass the media item
        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(requireContext())
        )
            .createMediaSource(mediaItem)

        // Finally assign this media source to the player
        player?.apply {
            setMediaSource(mediaSource)
            playWhenReady = true // start playing when the exoplayer has setup
            seekTo(0, 0L) // Start from the beginning
            prepare() // Change the state from idle.
        }.also {
            binding.exoPlayer.player = it
        }
    }

//    private fun preparePlayer(videoUrl: String, type: String) {
//        val uri = Uri.parse(videoUrl)
//        val mediaSource = buildMediaSource(uri, type)
//        simpleExoplayer.prepare(mediaSource)
//    }
//
//    private fun releasePlayer() {
//        playbackPosition = simpleExoplayer.currentPosition
//        simpleExoplayer.release()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}