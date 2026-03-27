package com.der3.home.presentations.side_menu.share_app.mvi

import com.der3.mvi.MviState

data class ShareAppState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val downloadLink: String = "",
) : MviState
