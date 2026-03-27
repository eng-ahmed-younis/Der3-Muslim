package com.der3.home.presentations.side_menu.about_der3.mvi

import com.der3.mvi.MviState

data class AboutDer3State(
    val version: String = "2.4.0",
    val isLoading: Boolean = false,
    val error: String? = null
) : MviState
