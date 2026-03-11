package com.der3.home.utils

import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import com.der3.model.TasbeehHapticType

fun performTasbeehHaptic(haptic: HapticFeedback, type: TasbeehHapticType) {
    when (type) {
        TasbeehHapticType.NONE -> {}
        TasbeehHapticType.SHORT -> {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
        TasbeehHapticType.LONG -> {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
        TasbeehHapticType.HEART_BEAT -> {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            // Note: True heartbeat pattern is limited in standard Compose HapticFeedback.
            // This is a simplified fallback.
        }
    }
}
