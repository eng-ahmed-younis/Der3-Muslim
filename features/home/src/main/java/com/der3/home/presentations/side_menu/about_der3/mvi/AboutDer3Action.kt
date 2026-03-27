package com.der3.home.presentations.side_menu.about_der3.mvi

import com.der3.mvi.MviAction

sealed class AboutDer3Action : MviAction {
    data class UpdateVersion(val version: String) : AboutDer3Action()
    data class UpdateLoading(val isLoading: Boolean) : AboutDer3Action()
    data class UpdateError(val error: String?) : AboutDer3Action()
}
