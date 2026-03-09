package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

interface SetAzkarVolumeUseCase {
    operator fun invoke(volume: Float)
}

class SetAzkarVolumeUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : SetAzkarVolumeUseCase {
    override fun invoke(volume: Float) {
        audioPlayer.setVolume(volume)
    }
}
