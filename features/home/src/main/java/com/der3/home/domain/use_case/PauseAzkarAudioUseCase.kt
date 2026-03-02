package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for pausing azkar audio.
 */
interface PauseAzkarAudioUseCase {
    operator fun invoke()
}


class PauseAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : PauseAzkarAudioUseCase {
    /** Pause current playback without releasing resources */
    override fun invoke() {
        audioPlayer.pause()
    }
}