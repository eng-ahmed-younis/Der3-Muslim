package com.der3.home.presentations.all_categories

import androidx.lifecycle.viewModelScope
import com.der3.shared.domain.use_case.GetAzkarCategoriesUseCase
import com.der3.home.data.mappers.toUiCategories
import com.der3.home.presentations.all_categories.mvi.AzkarCategoryActions
import com.der3.home.presentations.all_categories.mvi.AzkarCategoryIntent
import com.der3.home.presentations.all_categories.mvi.AzkarCategoryReducer
import com.der3.home.presentations.all_categories.mvi.AzkarCategoryState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
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
) : MviBaseViewModel<AzkarCategoryState, AzkarCategoryActions, AzkarCategoryIntent>(
    initialState = AzkarCategoryState(),
    reducer = AzkarCategoryReducer()
) {

    init {
        getAllAzkarCategories()
    }

    override fun handleIntent(intent: AzkarCategoryIntent) {
        when (intent) {
            is AzkarCategoryIntent.UpdateSearchQuery -> {
                onAction(AzkarCategoryActions.UpdateSearchQuery(intent.query))
                searchCategories(intent.query)
            }
            is AzkarCategoryIntent.SelectCategory -> {
                onEffect(
                    MviEffect.Navigate(
                        Der3NavigationRoute.CategoryDetailsScreen(
                            categoryId = intent.category.id,
                            categoryTitle = intent.category.title,
                            categorySubtitle = intent.category.subtitle,
                            categoryCount = intent.category.count
                        )
                    )
                )
            }
        }
    }

    private fun getAllAzkarCategories() {
        getAllCategoriesUseCase.invoke()
            .onStart { onAction(AzkarCategoryActions.OnLoading(true)) }
            .map { it.toUiCategories() }
            .onEach { onAction(AzkarCategoryActions.LoadAllAzkarCategory(category = it)) }
            .onCompletion { onAction(AzkarCategoryActions.OnLoading(false)) }
            .catch { onAction(AzkarCategoryActions.OnLoading(false)) }
            .launchIn(viewModelScope)
    }

    private fun searchCategories(query: String) {
        if (query.isBlank()) {
            onAction(AzkarCategoryActions.SearchQueryEmpty)
            return
        }
        val filtered = viewState.cachedCategories.filter { category ->
            category.title.normalizeArabic().contains(query.trim().normalizeArabic())
        }
        onAction(AzkarCategoryActions.LoadSearchAzkarCategory(category = filtered))
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