package com.der3.utils

import android.content.Context
import com.der3.model.UiText


fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.DynamicError -> message
        is UiText.ResourceError -> context.getString(messageId)
    }
}
