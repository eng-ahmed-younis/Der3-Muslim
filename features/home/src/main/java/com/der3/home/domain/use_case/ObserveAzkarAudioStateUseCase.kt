package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import com.der3.player.audio.model.AzkarAudioState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveAzkarAudioStateUseCase {
    operator fun invoke(): Flow<AzkarAudioState>
}

class ObserveAzkarAudioStateUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : ObserveAzkarAudioStateUseCase {

    override fun invoke(): Flow<AzkarAudioState> {
        return audioPlayer.audioState
    }
}