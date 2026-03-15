package com.der3.shared.utils

fun String.normalizeArabic(): String {
    return this
        .replace(Regex("[\u064B-\u065F\u0670]"), "") // Remove diacritics
        .replace(Regex("[إأآا]"), "ا")              // Normalize Alif
        .replace('ة', 'ه')                          // Normalize Ta Marbuta
        .replace('ى', 'ي')                          // Normalize Alif Maksura
        .trim()
}
