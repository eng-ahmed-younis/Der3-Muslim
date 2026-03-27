package com.der3.home.presentations.side_menu.contact_us.mvi

import com.der3.mvi.MviState

data class ContactUsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val name: String = "",
    val email: String = "",
    val message: String = "",
    val officialEmail: String = "der3.muslim@gmail.com",
    val website: String = "https://der3muslim.com",
    val telegram: String = "https://t.me/der3muslim",
    val facebook: String = "https://facebook.com/der3muslim"
) : MviState
