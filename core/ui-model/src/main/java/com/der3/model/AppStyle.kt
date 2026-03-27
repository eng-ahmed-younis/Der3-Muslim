package com.der3.model

sealed class AppStyle(val value: String) {
    data object LIGHT : AppStyle("light")
    data object DARK : AppStyle("dark")
    data object SYSTEM : AppStyle("system")
    companion object {
        fun valueOf(style: String): AppStyle {
            return when (style) {
                "light" -> LIGHT
                "dark" -> DARK
                "system" -> SYSTEM
                else -> SYSTEM
            }
        }
    }


}

