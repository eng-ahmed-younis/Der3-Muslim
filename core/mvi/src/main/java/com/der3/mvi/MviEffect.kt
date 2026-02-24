package com.der3.mvi


interface MviEffect {

    data class Navigate(val screen: Screens) : MviEffect

    data class OnErrorDialog(val error: String) : MviEffect

    data class OnSuccessDialog(val success: String) : MviEffect

}
