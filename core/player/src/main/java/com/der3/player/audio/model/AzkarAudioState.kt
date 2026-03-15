package com.der3.player.audio.model

/**
 * Represents the current state of azkar audio playback.
 */
data class AzkarAudioState(
    /** True if audio is currently playing */
    val isPlaying: Boolean = false,
    /** Path of the currently loaded audio file */
    val currentPath: String? = null,
    /** Current playback position in milliseconds */
    val currentPosition: Long = 0,
    /** Total duration of the audio in milliseconds */
    val duration: Long = 0,
    /** Error message if playback failed */
    val error: String? = null
) {
    val progress: Float
        get() = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f
}
