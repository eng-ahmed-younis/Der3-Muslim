package com.der3.home.presentations.all_categories

import androidx.lifecycle.viewModelScope
import com.der3.data.mappers.toUiCategories
import com.der3.data.use_case.GetAzkarCategoriesUseCase
import com.der3.data.use_case.SearchAzkarCategoriesUseCase
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
    private val getAllCategoriesUseCase: GetAzkarCategoriesUseCase,
) : MviBaseViewModel<AllCategoryState, AllCategoryActions, AllCategoryIntent>(
    initialState = AllCategoryState(),
    reducer = AllCategoryReducer()
) {

    init {
        getAllAzkarCategories()
    }

    override fun handleIntent(intent: AllCategoryIntent) {
        when (intent) {
            is AllCategoryIntent.UpdateSearchQuery -> {
                onAction(AllCategoryActions.UpdateSearchQuery(intent.query))
                searchCategories(intent.query)
            }
        }
    }

    private fun getAllAzkarCategories() {
        getAllCategoriesUseCase.invoke()
            .onStart { onAction(AllCategoryActions.OnLoading(true)) }
            .map { it.toUiCategories() }
            .onEach { onAction(AllCategoryActions.LoadAllAzkarCategory(category = it)) }
            .onCompletion { onAction(AllCategoryActions.OnLoading(false)) }
            .catch { onAction(AllCategoryActions.OnLoading(false)) }
            .launchIn(viewModelScope)
    }

    private fun searchCategories(query: String) {
        if (query.isBlank()) {
            onAction(AllCategoryActions.SearchQueryEmpty)
            return
        }
        val filtered = viewState.cachedCategories.filter { category ->
            category.title.normalizeArabic().contains(query.trim().normalizeArabic())
        }
        onAction(AllCategoryActions.LoadSearchAzkarCategory(category = filtered))
    }
}

private fun String.normalizeArabic(): String {
    return this
        .replace(Regex("[\u064B-\u065F\u0670]"), "")
        .replace(Regex("[إأآا]"), "ا")
        .replace('ة', 'ه')
        .replace('ى', 'ي')
        .trim()
}