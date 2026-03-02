package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for resuming azkar audio.
 */
interface ResumeAzkarAudioUseCase {
    operator fun invoke()
}



class ResumeAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : ResumeAzkarAudioUseCase {
    /** Resume from where playback was paused */
    override fun invoke() {
        audioPlayer.resume()
    }
}