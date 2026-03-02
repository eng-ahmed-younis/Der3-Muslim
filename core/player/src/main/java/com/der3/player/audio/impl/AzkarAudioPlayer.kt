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

        if (currentPath == assetPath && isAudioPlaying) return

        if (currentPath != assetPath) stop()

        try {
            mediaPlayer = MediaPlayer().apply {

                val afd = context.assets.openFd(assetPath)

                setDataSource(
                    afd.fileDescriptor,
                    afd.startOffset,
                    afd.length
                )

                prepare()
                start()

                currentPath = assetPath
                isAudioPlaying = true

                emitState()

                setOnCompletionListener {
                    isAudioPlaying = false
                    currentPath = null
                    emitState()
                    onPlaybackCompleted?.invoke()
                }

                setOnErrorListener { _, what, extra ->
                    isAudioPlaying = false

                    val errorMsg =
                        "MediaPlayer error: what=$what extra=$extra"

                    _audioState.value = AzkarAudioState(
                        isPlaying = false,
                        currentPath = null,
                        error = errorMsg
                    )

                    onError?.invoke(errorMsg)
                    true
                }
            }

        } catch (e: Exception) {

            isAudioPlaying = false
            currentPath = null

            val errorMsg = e.message ?: "Unknown audio error"

            _audioState.value = AzkarAudioState(
                isPlaying = false,
                currentPath = null,
                error = errorMsg
            )

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
        mediaPlayer?.start()
        isAudioPlaying = true
        emitState()
    }

    // ─────────────────────────────────────────────
    // Stop
    // ─────────────────────────────────────────────

    override fun stop() {
        mediaPlayer?.stop()
        isAudioPlaying = false
        currentPath = null
        release()
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