package com.der3.home.domain.use_case

import com.der3.player.audio.api.AudioPlayer
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class AudioUseCasesTest {

    private val audioPlayer: AudioPlayer = mockk(relaxed = true)

    @Test
    fun `PlayAzkarAudioUseCaseImpl invokes play on audioPlayer`() {
        val useCase = PlayAzkarAudioUseCaseImpl(audioPlayer)
        useCase.invoke("path/to/audio.mp3")

        verify(exactly = 1) { audioPlayer.play("path/to/audio.mp3") }
    }

    @Test
    fun `PauseAzkarAudioUseCaseImpl invokes pause on audioPlayer`() {
        val useCase = PauseAzkarAudioUseCaseImpl(audioPlayer)
        useCase.invoke()

        verify(exactly = 1) { audioPlayer.pause() }
    }

    @Test
    fun `ResumeAzkarAudioUseCaseImpl invokes resume on audioPlayer`() {
        val useCase = ResumeAzkarAudioUseCaseImpl(audioPlayer)
        useCase.invoke()

        verify(exactly = 1) { audioPlayer.resume() }
    }

    @Test
    fun `StopAzkarAudioUseCaseImpl invokes stop on audioPlayer`() {
        val useCase = StopAzkarAudioUseCaseImpl(audioPlayer)
        useCase.invoke()

        verify(exactly = 1) { audioPlayer.stop() }
    }

    @Test
    fun `SetAzkarPlaybackSpeedUseCaseImpl invokes setPlaybackSpeed on audioPlayer`() {
        val useCase = SetAzkarPlaybackSpeedUseCaseImpl(audioPlayer)
        useCase.invoke(1.5f)

        verify(exactly = 1) { audioPlayer.setPlaybackSpeed(1.5f) }
    }
}
