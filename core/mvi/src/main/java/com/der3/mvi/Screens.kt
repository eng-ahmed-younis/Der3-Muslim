package com.der3.mvi

import androidx.annotation.Keep
import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Keep
@Stable
interface Screens {
    @Keep
    @Serializable
    data class Back(
        val payload: Map<String, String>? = null
    ) : Screens

    @Keep
    @Serializable
    data class BackTo(
        val screen: Screens,
        val exclusive: Boolean = false,
        val payload: Map<String, String>? = null
    ) : Screens

    @Keep
    @Serializable
    data class NavigateToRoot(
        val screen: Screens,
        val exclusive: Boolean = false
    ) : Screens

    @Keep
    @Serializable
    data class Replace(
        val newScreen: Screens,
        val oldScreen: KClass<out Screens>,
        val payload: Map<String, String>? = null
    ) : Screens

    @Keep
    @Stable
    @Serializable
    data class NavigateKeepingOnly(
        val targetScreen: Screens,
        val keepScreen: KClass<out Screens>
    ) : Screens
}