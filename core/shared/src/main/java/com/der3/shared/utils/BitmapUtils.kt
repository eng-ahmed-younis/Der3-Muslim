package com.der3.shared.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.stringToBitmap(): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}