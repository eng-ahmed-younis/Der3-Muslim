package com.der3.home.presentations.category_details.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class CategoryDetailsReducer @Inject constructor() : Reducer<CategoryDetailsAction, CategoryDetailsState> {
    override fun reduce(
        action: CategoryDetailsAction,
        state: CategoryDetailsState
    ): CategoryDetailsState {

        return when (action) {
            is CategoryDetailsAction.OnLoading -> state.copy(isLoading = action.isLoading)
            is CategoryDetailsAction.OnCategoryLoaded -> {
                state.copy(
                    azkarItems = action.azkars,
                    zakeTextList = action.azkars.map { it.text }
                )
            }

            is CategoryDetailsAction.GetCategoryParams -> {
                state.copy(
                    categoryId = action.categoryId,
                    categoryTitle = action.categoryTitle,
                    categorySubtitle = action.categorySubtitle,
                    categoryCount = action.categoryCount
                )
            }
        }
    }


}