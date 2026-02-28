package com.der3.home.presentations.all_categories.mvi

import androidx.compose.runtime.Stable
import com.der3.ui.models.CategoryUi
import com.der3.mvi.MviState
import com.der3.utils.DEFAULT_BOOLEAN

@Stable
data class AllCategoryState (
    val isLoading: Boolean = DEFAULT_BOOLEAN,
    val categories: List<CategoryUi> = emptyList(),
    val cachedCategories: List<CategoryUi> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
): MviState