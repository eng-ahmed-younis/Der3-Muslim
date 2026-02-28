package com.der3.model

enum class ZekrLength(
    val minSeconds: Int,
    val maxSeconds: Int
) {
    SHORT(10, 15),
    MEDIUM(20, 30),
    LONG(35, 45);

    fun averageSeconds(): Int = (minSeconds + maxSeconds) / 2
}
