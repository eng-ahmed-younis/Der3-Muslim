package com.der3.shared.params

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Serializable
@Keep
data class ZekrDetailsParams(
    val zekrId: Int,
    val categoryId: Int,
  /*  val categoryTitle: String,
    val categorySubtitle: String,
    val categoryCount: String*/
)

