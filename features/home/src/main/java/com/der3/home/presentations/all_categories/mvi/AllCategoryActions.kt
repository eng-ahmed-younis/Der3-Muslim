package com.der3.home.presentations.all_categories.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviAction
import com.der3.ui.models.CategoryUi


@Stable
sealed interface AllCategoryActions : MviAction {
    data class OnLoading(val isLoading: Boolean) : AllCategoryActions
    data class LoadAllAzkarCategory(val category: List<CategoryUi>) : AllCategoryActions
    data class UpdateSearchQuery(val query: String) : AllCategoryActions
}