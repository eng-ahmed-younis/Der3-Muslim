package com.der3.model

sealed class UiText {
    data class DynamicError(val message: String) : UiText()
    data class ResourceError(val messageId: Int) : UiText()
}
