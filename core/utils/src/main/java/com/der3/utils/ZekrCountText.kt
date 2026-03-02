package com.der3.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.der3.ui.R

/**
 * ──────────────────────────────────────────────────────────────────────────────
 * Arabic Plural Utilities
 * ──────────────────────────────────────────────────────────────────────────────
 *
 * These extensions rely on Android plural resources (CLDR rules).
 * Arabic supports 6 plural categories:
 *
 * zero  → 0
 * one   → 1
 * two   → 2
 * few   → 3–10
 * many  → 11–99
 * other → 100+
 *
 * -------------------------------------------------------------------------------
 * | Input | toZekrText() Result        | Rule  | Explanation                |
 * |-------|----------------------------|-------|----------------------------|
 * | 0     | لا توجد أذكار              | zero  | Special zero case          |
 * | 1     | ذكر واحد                   | one   | Singular                   |
 * | 2     | ذكران                      | two   | Dual form                  |
 * | 5     | ٥ أذكار                    | few   | 3–10 uses plural           |
 * | 15    | ١٥ ذكراً                   | many  | 11–99 singular + tanween   |
 * | 100   | ١٠٠ ذكر                    | other | 100+ singular              |
 * -------------------------------------------------------------------------------
 *
 * Example usage:
 *
 * Text(text = 5.toZekrText())
 * Text(text = 3.toTimesText())
 *
 * ──────────────────────────────────────────────────────────────────────────────
 */


@Composable
fun Int.toZekrCountTex(): String {
    return pluralStringResource(
        id = R.plurals.times_count,
        count = this,
        this
    )
}


@Composable
fun Int.toPluralText(pluralRes: Int): String {
    return pluralStringResource(
        id = pluralRes,
        count = this,
        this
    )
}

@Composable
fun Int.toZekrText(): String {
    return pluralStringResource(
        id = R.plurals.zekr_count,
        count = this,
        this
    )
}

@Composable
fun Int.toMinutesText(): String {
    return pluralStringResource(
        id = R.plurals.minutes_count,
        count = this,
        this
    )
}

/**
 * ─── Usage Examples (Arabic Plurals) ──────────────────────────────────────────
 *
 * | Input (Int) | Function Call             | Output (Arabic)        | Rule     |
 * |-------------|---------------------------|------------------------|----------|
 * | 0           | ZekrCountText(0)          | لا توجد أذكار          | Zero     |
 * | 1           | ZekrCountText(1)          | ذكر واحد               | One      |
 * | 2           | ZekrCountText(2)          | ذكران                  | Two      |
 * | 5           | 5.toZekrText()            | ٥ أذكار                | Few      |
 * | 15          | 15.toZekrText()           | ١٥ ذكراً               | Many     |
 * | 100         | 100.toZekrText()          | ١٠٠ ذكر                | Other    |
 * ──────────────────────────────────────────────────────────────────────────────
 */