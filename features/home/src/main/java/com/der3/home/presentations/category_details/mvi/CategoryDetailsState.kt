package com.der3.home.presentations.category_details.mvi

import androidx.compose.runtime.Immutable
import com.der3.home.domain.model.ZekrUiModel
import com.der3.mvi.MviState
import com.der3.ui.models.CategoryUi

@Immutable
data class CategoryDetailsState (
    val isLoading: Boolean = false,
    val categoryId: Int = -1,
    val categoryTitle: String = "",
    val categorySubtitle: String = "",
    val categoryCount: String = "",
    val category: CategoryUi? = null,
    val zakeTextList: List<String> = emptyList() ,
    val azkarItems: List<ZekrUiModel> = emptyList(),
    val error: String? = null
) : MviState