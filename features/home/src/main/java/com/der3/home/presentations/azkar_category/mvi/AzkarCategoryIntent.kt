package com.der3.home.presentations.azkar_category.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface AzkarCategoryIntent : MviIntent {

    data class UpdateSearchQuery(val query: String) : AzkarCategoryIntent
    data class SelectCategory(val category: com.der3.ui.models.CategoryUi) : AzkarCategoryIntent
}