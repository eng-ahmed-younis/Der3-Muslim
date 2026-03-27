package com.der3.home.presentations.azkar_category.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviAction
import com.der3.ui.models.CategoryUi


@Stable
sealed interface AzkarCategoryActions : MviAction {
    data class OnLoading(val isLoading: Boolean) : AzkarCategoryActions
    data class LoadAllAzkarCategory(val category: List<CategoryUi>) : AzkarCategoryActions

    data class LoadSearchAzkarCategory(val category: List<CategoryUi>) : AzkarCategoryActions

    data object SearchQueryEmpty : AzkarCategoryActions
    data class UpdateSearchQuery(val query: String) : AzkarCategoryActions
}