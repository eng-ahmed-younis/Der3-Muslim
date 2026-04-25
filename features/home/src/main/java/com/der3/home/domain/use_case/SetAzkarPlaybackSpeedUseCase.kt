package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

interface SetAzkarPlaybackSpeedUseCase {
    operator fun invoke(speed: Float)
}

class SetAzkarPlaybackSpeedUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : SetAzkarPlaybackSpeedUseCase {
    override fun invoke(speed: Float) {
        audioPlayer.setPlaybackSpeed(speed)
    }
}
