package com.der3.home.presentations.all_categories

import androidx.lifecycle.viewModelScope
import com.der3.data.mappers.toUiCategories
import com.der3.data.use_case.GetAzkarCategoriesUseCase
import com.der3.home.presentations.all_categories.mvi.AllCategoryActions
import com.der3.home.presentations.all_categories.mvi.AllCategoryIntent
import com.der3.home.presentations.all_categories.mvi.AllCategoryReducer
import com.der3.home.presentations.all_categories.mvi.AllCategoryState
import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.mvi.MviBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel
class AllCategoryViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAzkarCategoriesUseCase
) : MviBaseViewModel<AllCategoryState, AllCategoryActions, AllCategoryIntent>(
    initialState = AllCategoryState(),
    reducer = AllCategoryReducer()
) {

    init {
        getAllAzkarCategories()
    }


    override fun handleIntent(intent: AllCategoryIntent) {
        when(intent){
            is AllCategoryIntent.UpdateSearchQuery -> {

              onAction(AllCategoryActions.UpdateSearchQuery(intent.query))
            }
        }
    }


    private fun getAllAzkarCategories() {
        getAllCategoriesUseCase.invoke()
            .onStart {
                onAction(AllCategoryActions.OnLoading(true))
            }
            .map { categories ->
                categories.toUiCategories()  // ← convert here
            }
            .onEach { uiCategories ->
                onAction(AllCategoryActions.LoadAllAzkarCategory(category = uiCategories))
            }
            .onCompletion {
                onAction(AllCategoryActions.OnLoading(false))
            }
            .catch {
                onAction(AllCategoryActions.OnLoading(false))
            }
            .launchIn(viewModelScope)
    }
}