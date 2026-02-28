package com.der3.home.presentations.all_categories.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class AllCategoryReducer @Inject constructor() : Reducer<AllCategoryActions,AllCategoryState> {


    override fun reduce(
        action: AllCategoryActions,
        state: AllCategoryState
    ): AllCategoryState {
        return when(action){
            is AllCategoryActions.UpdateSearchQuery -> {
                state.copy(searchQuery = action.query)
            }

            is AllCategoryActions.LoadAllAzkarCategory -> {
                state.copy(
                    categories = action.category,
                    cachedCategories = action.category
                )
            }

            is AllCategoryActions.OnLoading -> {
                state.copy(isLoading = action.isLoading)
            }

            is AllCategoryActions.LoadSearchAzkarCategory -> {
                state.copy(categories = action.category)
            }

            AllCategoryActions.SearchQueryEmpty -> {
                state.copy(categories = state.cachedCategories)
            }
        }
    }
}