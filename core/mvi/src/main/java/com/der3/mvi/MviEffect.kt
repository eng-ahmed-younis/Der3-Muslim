package com.der3.mvi

import com.der3.model.ShareZekrType
import com.der3.model.UiText
import com.der3.screens.Screens


interface MviEffect {

    data class Navigate(val screen: Screens) : MviEffect

    data class OnErrorDialog(val error: UiText) : MviEffect

    data class OnSuccessDialog(val success: String) : MviEffect

    data class Share(
        val text: String,
        val imageUri: String? = null,
        val type: ShareZekrType = if (imageUri != null) ShareZekrType.TEXT_AND_IMAGE else ShareZekrType.TEXT_ONLY
    ) : MviEffect

    data object CaptureAndShareImage : MviEffect


}
