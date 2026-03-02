package com.der3.home.presentations.category_details.mvi

import androidx.compose.runtime.Stable
import com.der3.home.domain.ZekrUiModel
import com.der3.mvi.MviAction

@Stable
sealed interface CategoryDetailsAction : MviAction {

    data class OnLoading(val isLoading: Boolean) : CategoryDetailsAction

    data class OnCategoryLoaded(val azkars: List<ZekrUiModel>) : CategoryDetailsAction

    data class GetCategoryParams(
        val categoryId: Int,
        val categoryTitle: String,
        val categorySubtitle: String,
        val categoryCount: String
    ) : CategoryDetailsAction
}