package com.der3.player.audio.model

/**
 * Represents the current state of azkar audio playback.
 */
data class AzkarAudioState(
    /** True if audio is currently playing */
    val isPlaying: Boolean = false,
    /** Path of the currently loaded audio file */
    val currentPath: String? = null,
    /** Error message if playback failed */
    val error: String? = null
)