package com.der3.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

object PalletColors {

    val iconBackgroundColors = listOf(

        // 🔴 Soft Reds
        Color(0xFFFF7C7C), // rose mist
        Color(0xFFFF6B6B), // blush cloud
        Color(0xFFF55F5F), // soft coral pastel

        // 🟢 Fresh Greens
        Color(0xFF7DC98A), // fresh mint
        Color(0xFF5BBF8A), // jade light
        Color(0xFF4DB37D), // eucalyptus

        // 🔵 Calm Blues
        Color(0xFF6EB3ED), // sky mist
        Color(0xFF57A5E8), // airy blue
        Color(0xFF4096E3), // cloud blue

        // 🟣 Elegant Purples
        Color(0xFFBB78ED), // lilac frost
        Color(0xFFAA62E6), // lavender mist
        Color(0xFF9850DE), // soft violet

        // 🩵 Extra Cool Neutrals
        Color(0xFF4DCEC7), // ice mint
        Color(0xFF7080ED), // cool mist
        Color(0xFF8A94CC)  // soft neutral gray-blue
    )

    fun getStrongTint(bgColor: Color): Color {
        return lerp(bgColor, Color(0xFF000000), 0.55f)
    }
}