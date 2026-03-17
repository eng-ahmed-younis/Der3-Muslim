package com.der3.shared.params

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Serializable
@Keep
data class MasbahaParams (
    val openFromSection: Boolean = false
)