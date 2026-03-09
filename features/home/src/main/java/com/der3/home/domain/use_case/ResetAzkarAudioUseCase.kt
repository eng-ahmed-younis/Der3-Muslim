package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

interface ResetAzkarAudioUseCase {
    operator fun invoke(audioPath: String)
}

class ResetAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : ResetAzkarAudioUseCase {
    override fun invoke(audioPath: String) {
        // if have already playing path then reset it not play zekr path
        if (audioPlayer.audioState.value.currentPath != null) {
            audioPlayer.reset()
        } else {
            audioPlayer.play(audioPath)
        }
    }
}
