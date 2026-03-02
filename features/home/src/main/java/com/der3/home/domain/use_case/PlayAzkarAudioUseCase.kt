package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import javax.inject.Inject

/**
 * Use case for playing azkar audio.
 * Abstracts audio playback logic from the ViewModel.
 */
interface PlayAzkarAudioUseCase {
    operator fun invoke(audioPath: String)
}



class PlayAzkarAudioUseCaseImpl @Inject constructor(
    private val audioPlayer: AudioPlayer
) : PlayAzkarAudioUseCase {
    /**
     * Play audio from the given path.
     * e.g "/audio/75.mp3" → assets/audio/75.mp3
     */
    override fun invoke(audioPath: String) {
        audioPlayer.play(audioPath)
    }
}