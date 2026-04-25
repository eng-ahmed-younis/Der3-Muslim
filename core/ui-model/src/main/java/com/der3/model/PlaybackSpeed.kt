package com.der3.model

enum class PlaybackSpeed(val value: Float, val label: String) {
    X0_25(0.25f, "0.25x"),
    X0_5(0.5f, "0.5x"),
    X0_75(0.75f, "0.75x"),
    X1_0(1.0f, "عادي"),
    X1_25(1.25f, "1.25x"),
    X1_5(1.5f, "1.5x"),
    X1_75(1.75f, "1.75x"),
    X2_0(2.0f, "2x");

    companion object {
        fun fromFloat(value: Float): PlaybackSpeed {
            return entries.find { it.value == value } ?: X1_0
        }
    }
}
