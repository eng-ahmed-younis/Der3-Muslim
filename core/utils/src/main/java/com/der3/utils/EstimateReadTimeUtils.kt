package com.der3.utils

import com.der3.model.ZekrLength
import kotlin.math.ceil

/**
 * Estimates total reading time (in minutes) for a list of Azkar.
 *
 * The estimation process:
 * 1. Detect the length category of each zekr (SHORT / MEDIUM / LONG)
 * 2. Get the average reading time in seconds from the enum
 * 3. Sum all seconds
 * 4. Convert to minutes and round up
 *
 * @param azkar List of zekr texts
 * @return Estimated total reading time in whole minutes (rounded up)
 */
fun estimateReadingTimeInMinutes(azkar: List<String>): Int {

    // Calculate total estimated seconds for all azkar
    val totalSeconds = azkar.sumOf { zekr ->
        detectZekrLength(zekr).averageSeconds()
    }

    // Convert seconds to minutes and round up
    // Example: 8.2 minutes → 9 minutes
    return ceil(totalSeconds / 60.0).toInt()
}


/**
 * Determines the ZekrLength category based on word count.
 *
 * Classification rules:
 * - 1 to 15 words  → SHORT
 * - 16 to 35 words → MEDIUM
 * - 36+ words      → LONG
 *
 * @param text Zekr text
 * @return ZekrLength enum value
 */
fun detectZekrLength(text: String): ZekrLength {

    // Count number of words (handles multiple spaces & line breaks)
    val wordCount = text
        .trim()
        .split("\\s+".toRegex())
        .size

    // Return category based on word count
    return when {
        wordCount <= 15 -> ZekrLength.SHORT
        wordCount <= 35 -> ZekrLength.MEDIUM
        else -> ZekrLength.LONG
    }
}
