package com.der3.home.presentations.all_categories.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class AzkarCategoryReducer @Inject constructor() : Reducer<AzkarCategoryActions,AzkarCategoryState> {


    override fun reduce(
        action: AzkarCategoryActions,
        state: AzkarCategoryState
    ): AzkarCategoryState {
        return when(action){
            is AzkarCategoryActions.UpdateSearchQuery -> {
                state.copy(searchQuery = action.query)
            }

            is AzkarCategoryActions.LoadAllAzkarCategory -> {
                state.copy(
                    categories = action.category,
                    cachedCategories = action.category
                )
            }

            is AzkarCategoryActions.OnLoading -> {
                state.copy(isLoading = action.isLoading)
            }

            is AzkarCategoryActions.LoadSearchAzkarCategory -> {
                state.copy(categories = action.category)
            }

            AzkarCategoryActions.SearchQueryEmpty -> {
                state.copy(categories = state.cachedCategories)
            }
        }
    }
}