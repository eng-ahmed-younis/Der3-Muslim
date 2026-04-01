package com.der3.player.audio.impl

import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.der3.data_store.api.DataStoreRepository
import com.der3.player.audio.api.AudioPlayer
import com.der3.player.audio.model.AzkarAudioState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AzkarAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : AudioPlayer, DefaultLifecycleObserver {

    private var mediaPlayer: MediaPlayer? = null
    private var currentPath: String? = null
    private var progressJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        scope.launch {
            dataStoreRepository.playbackSpeedFlow.collect { speed ->
                setPlaybackSpeed(speed)
            }
        }
    }

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

        val cleanPath = audioPath.removePrefix("/")

        // If already playing this exact file, do nothing
        if (currentPath == cleanPath && isAudioPlaying) return

        // Always stop and release any existing player before starting a new one
        stop()

        try {
            mediaPlayer = if (cleanPath.startsWith("raw/")) {
                val resName = cleanPath.removePrefix("raw/").substringBefore(".")
                val resId = context.resources.getIdentifier(resName, "raw", context.packageName)
                if (resId == 0) throw Exception("Raw resource not found: $resName")
                MediaPlayer.create(context, resId)
            } else {
                MediaPlayer().apply {
                    val afd = context.assets.openFd(cleanPath)
                    setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    prepare()
                }
            }

            mediaPlayer?.apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    val speed = dataStoreRepository.playbackSpeed
                    playbackParams = playbackParams.setSpeed(speed)
                }
                start()

                currentPath = cleanPath
                isAudioPlaying = true
                startProgressUpdate()
                emitState()

                setOnCompletionListener {
                    isAudioPlaying = false
                    stopProgressUpdate()
                    // Keep currentPath so we know what was last played
                    emitState()
                    onPlaybackCompleted?.invoke()
                }

                setOnErrorListener { _, what, extra ->
                    isAudioPlaying = false
                    stopProgressUpdate()
                    val errorMsg = "MediaPlayer error: what=$what extra=$extra"
                    _audioState.value = AzkarAudioState(isPlaying = false, currentPath = currentPath, error = errorMsg)
                    onError?.invoke(errorMsg)
                    true
                }
            }
        } catch (e: Exception) {
            isAudioPlaying = false
            stopProgressUpdate()
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
        stopProgressUpdate()
        emitState()
    }

    // ─────────────────────────────────────────────
    // Resume
    // ─────────────────────────────────────────────

    override fun resume() {
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            isAudioPlaying = true
            startProgressUpdate()
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
        stopProgressUpdate()
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

    override fun setPlaybackSpeed(speed: Float) {
        try {
            mediaPlayer?.let {
                val params = it.playbackParams
                params.speed = speed
                it.playbackParams = params
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            currentPath = currentPath,
            currentPosition = mediaPlayer?.currentPosition?.toLong() ?: 0L,
            duration = mediaPlayer?.duration?.toLong() ?: 0L
        )
    }

    private fun startProgressUpdate() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                emitState()
                delay(100)
            }
        }
    }

    private fun stopProgressUpdate() {
        progressJob?.cancel()
        progressJob = null
    }
}