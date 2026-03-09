package com.der3.player.audio.impl

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.der3.player.audio.api.AudioPlayer
import com.der3.player.audio.model.AzkarAudioState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AzkarAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioPlayer, DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private var currentPath: String? = null

    // ─────────────────────────────────────────────
    // Reactive State
    // ─────────────────────────────────────────────

    private val _audioState = MutableStateFlow(AzkarAudioState())
    override val audioState: StateFlow<AzkarAudioState> =
        _audioState.asStateFlow()

    override var isAudioPlaying: Boolean = false
        private set

    override var onPlaybackCompleted: (() -> Unit)? = null
    override var onError: ((String) -> Unit)? = null

    // ─────────────────────────────────────────────
    // Play
    // ─────────────────────────────────────────────

    override fun play(audioPath: String) {

        val assetPath = audioPath.removePrefix("/")

        // If already playing this exact file, do nothing
        if (currentPath == assetPath && isAudioPlaying) return

        // Always stop and release any existing player before starting a new one
        stop()

        try {
            mediaPlayer = MediaPlayer().apply {
                val afd = context.assets.openFd(assetPath)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                start()

                currentPath = assetPath
                isAudioPlaying = true
                emitState()

                setOnCompletionListener {
                    isAudioPlaying = false
                    // Keep currentPath so we know what was last played
                    emitState()
                    onPlaybackCompleted?.invoke()
                }

                setOnErrorListener { _, what, extra ->
                    isAudioPlaying = false
                    val errorMsg = "MediaPlayer error: what=$what extra=$extra"
                    _audioState.value = AzkarAudioState(isPlaying = false, currentPath = currentPath, error = errorMsg)
                    onError?.invoke(errorMsg)
                    true
                }
            }
        } catch (e: Exception) {
            isAudioPlaying = false
            val errorMsg = e.message ?: "Unknown audio error"
            _audioState.value = AzkarAudioState(isPlaying = false, currentPath = currentPath, error = errorMsg)
            onError?.invoke(errorMsg)
            release()
        }
    }

    // ─────────────────────────────────────────────
    // Pause
    // ─────────────────────────────────────────────

    override fun pause() {
        mediaPlayer?.pause()
        isAudioPlaying = false
        emitState()
    }

    // ─────────────────────────────────────────────
    // Resume
    // ─────────────────────────────────────────────

    override fun resume() {
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            isAudioPlaying = true
            emitState()
        } else if (currentPath != null) {
            // If player was released but we have a path, restart it
            play(currentPath!!)
        }
    }

    // ─────────────────────────────────────────────
    // Stop
    // ─────────────────────────────────────────────

    override fun stop() {
        mediaPlayer?.stop()
        release() // This sets mediaPlayer = null
        isAudioPlaying = false
        // Note: we don't clear currentPath here so reset/resume can use it
        emitState()
    }

    // ─────────────────────────────────────────────
    // Toggle
    // ─────────────────────────────────────────────

    override fun togglePlayPause(audioPath: String) {
        val assetPath = audioPath.removePrefix("/")

        when {
            isAudioPlaying && currentPath == assetPath -> pause()
            !isAudioPlaying && currentPath == assetPath -> resume()
            else -> play(audioPath)
        }
    }

    // ─────────────────────────────────────────────
    // Release
    // ─────────────────────────────────────────────

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun reset() {
        val path = currentPath
        if (mediaPlayer != null) {
            mediaPlayer?.seekTo(0)
            if (!isAudioPlaying) {
                mediaPlayer?.start()
                isAudioPlaying = true
                emitState()
            }
        } else if (path != null) {
            play(path)
        }
    }

    override fun setVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
    }

    // ─────────────────────────────────────────────
    // Lifecycle
    // ─────────────────────────────────────────────

    override fun onPause(owner: LifecycleOwner) {
        pause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        release()
    }

    // ─────────────────────────────────────────────
    // Helper
    // ─────────────────────────────────────────────

    private fun emitState() {
        _audioState.value = AzkarAudioState(
            isPlaying = isAudioPlaying,
            currentPath = currentPath
        )
    }
}