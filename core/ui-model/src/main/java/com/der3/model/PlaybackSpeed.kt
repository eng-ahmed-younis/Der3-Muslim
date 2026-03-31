package com.der3.model

enum class PlaybackSpeed(val value: Float) {
    X0_5(0.5f),
    X0_75(0.75f),
    X1_0(1.0f),
    X1_25(1.25f),
    X1_5(1.5f),
    X2_0(2.0f);

    companion object {
        // Helper to find the enum from a float value
        fun fromFloat(value: Float): PlaybackSpeed {
            return entries.find { it.value == value } ?: X1_0
        }
    }
}
