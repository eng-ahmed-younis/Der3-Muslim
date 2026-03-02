package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for toggling play/pause of azkar audio.
 * - Same file playing → pause
 * - Same file paused → resume
 * - Different file → stop current and play new
 */
interface ToggleAzkarAudioUseCase {
    operator fun invoke(audioPath: String)
}


class ToggleAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : ToggleAzkarAudioUseCase {
    /**
     * Smart toggle:
     * - Same file playing → pause
     * - Same file paused → resume
     * - Different file → stop current and play new
     */
    override fun invoke(audioPath: String) {
        audioPlayer.togglePlayPause(audioPath)
    }
}