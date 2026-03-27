package com.der3.home.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.der3.model.ShareAppType

data class ShareItem(
    val type: ShareAppType,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
)
