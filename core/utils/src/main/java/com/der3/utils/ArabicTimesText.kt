package com.der3.utils

import com.der3.model.TimeWordType

fun Int.toArabicTimesText(): String {
    val type = when {
        this == 1 -> TimeWordType.SINGLE
        this == 2 -> TimeWordType.DUAL
        this in 3..10 -> TimeWordType.PLURAL
        else -> TimeWordType.SINGLE
    }

    val word = when (type) {
        TimeWordType.SINGLE -> "مرة"
        TimeWordType.DUAL -> "مرتين"
        TimeWordType.PLURAL -> "مرات"
    }

    return if (this == 2) {
        word
    } else {
        "$this $word"
    }
}
