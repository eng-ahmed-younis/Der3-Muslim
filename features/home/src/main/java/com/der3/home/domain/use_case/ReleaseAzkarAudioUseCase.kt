package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for releasing audio player resources.
 */
interface ReleaseAzkarAudioUseCase {
    operator fun invoke()
}



class ReleaseAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : ReleaseAzkarAudioUseCase {
    /** Release MediaPlayer and free memory */
    override fun invoke() {
        audioPlayer.release()
    }
}