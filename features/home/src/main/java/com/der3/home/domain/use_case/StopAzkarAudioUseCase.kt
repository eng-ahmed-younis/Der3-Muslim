package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for stopping azkar audio and releasing resources.
 */
interface StopAzkarAudioUseCase {
    operator fun invoke()
}


class StopAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : StopAzkarAudioUseCase {
    /** Stop playback, reset state and release resources */
    override fun invoke() {
        audioPlayer.stop()
    }
}
