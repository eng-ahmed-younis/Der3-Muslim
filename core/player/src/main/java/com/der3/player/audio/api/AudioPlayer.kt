package com.der3.player.audio.api

import com.der3.player.audio.model.AzkarAudioState
import kotlinx.coroutines.flow.StateFlow


/**
 * Abstraction for audio playback.
 * Allows swapping implementations (e.g. MediaPlayer, ExoPlayer) without changing ViewModels.
 */
interface AudioPlayer {

    /** True if audio is currently playing */
    val isAudioPlaying: Boolean

    /** Called when playback finishes naturally */
    var onPlaybackCompleted: (() -> Unit)?

    /** Called when an error occurs during playback */
    var onError: ((String) -> Unit)?

    /**
     * Play audio from the given path.
     * Path comes directly from JSON e.g "/audio/75.mp3"
     * which maps to assets/audio/75.mp3
     */
    fun play(audioPath: String)

    /** Pause the current playback */
    fun pause()

    /** Resume paused playback */
    fun resume()

    /** Stop playback and reset state */
    fun stop()

    /**
     * Toggle between play and pause.
     * - If same file is playing → pause
     * - If same file is paused → resume
     * - If different file → stop current and play new
     */
    fun togglePlayPause(audioPath: String)

    /** Release MediaPlayer resources */
    fun release()

    /** Reset the audio player to the beginning of the track */
    fun reset()

    /**
     * Set the volume of the audio player.
     * @param volume Value between 0.0 and 1.0
     */
    fun setVolume(volume: Float)

    /**
     * Set the playback speed of the audio player.
     * @param speed Speed multiplier (e.g., 1.0f for normal speed)
     */
    fun setPlaybackSpeed(speed: Float)

    val audioState: StateFlow<AzkarAudioState>
}