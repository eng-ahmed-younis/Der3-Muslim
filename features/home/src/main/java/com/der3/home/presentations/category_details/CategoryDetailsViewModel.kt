package com.der3.home.presentations.category_details

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.der3.data.params.CategoryDetailsParams
import com.der3.data.use_case.GetAzkarCategoryByIdUseCase
import com.der3.home.data.mappers.toZekrUiModels
import com.der3.home.di.factory.CategoryDetailsViewModelFactory
import com.der3.home.presentations.category_details.mvi.CategoryDetailsAction
import com.der3.home.presentations.category_details.mvi.CategoryDetailsIntent
import com.der3.home.presentations.category_details.mvi.CategoryDetailsReducer
import com.der3.home.presentations.category_details.mvi.CategoryDetailsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart


@HiltViewModel(assistedFactory = CategoryDetailsViewModelFactory::class)
class CategoryDetailsViewModel @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted private val params: CategoryDetailsParams,
    private val getAzkarCategoryByIdUseCase: GetAzkarCategoryByIdUseCase
) : MviBaseViewModel<CategoryDetailsState, CategoryDetailsAction, CategoryDetailsIntent>(
    initialState = CategoryDetailsState(
    ),
    reducer = CategoryDetailsReducer()
) {


    init {
        onAction(
            CategoryDetailsAction.GetCategoryParams(
                categoryId = params.categoryId,
                categoryTitle = params.categoryTitle,
                categorySubtitle = params.categorySubtitle,
                categoryCount = params.categoryCount
            )
        )

        getCategoryDetails(params.categoryId)

    }


    override fun handleIntent(intent: CategoryDetailsIntent) {
        when(intent){
            is CategoryDetailsIntent.OnBackClick -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
            is CategoryDetailsIntent.OnZekrClick -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.ZekrDetailsScreen(intent.zekrId)))
            }
            else -> {}
           // is AzkarDetailsIntent.OnShareClick -> onAction(AzkarDetailsAction.OnShareClick(intent.zekr))
        }

    }


    private fun getCategoryDetails(
        categoryId: Int
    ) {
        getAzkarCategoryByIdUseCase.invoke(id = categoryId)
            .onStart { onAction(CategoryDetailsAction.OnLoading(isLoading = true)) }
            .map {
                it?.toZekrUiModels()
            }
            .onEach { azkars ->
                if (azkars == null) return@onEach
                onAction(
                    CategoryDetailsAction.OnCategoryLoaded(
                        azkars = azkars
                    )
                )
                /*       if (category == null) return@onEach

                       viewModelScope.launch {
                           // Run both mapping operations in parallel
                           val uiCategoryDeferred = async { category.toUiCategory() }
                           val zekrModelsDeferred = async { category.toZekrUiModels() }

                           // Wait for both to finish
                           val uiCategory = uiCategoryDeferred.await()
                           val zekrModels = zekrModelsDeferred.await()

                           awaitAll(uiCategoryDeferred, zekrModelsDeferred)

                           // Update the action with all the data once ready
                           onAction(
                               AzkarDetailsAction.OnCategoryLoaded(
                                   category = uiCategory ?: CategoryUi(),
                                   azkar = zekrModels ?: emptyList()
                               )
                           )
                       }*/
            }.onCompletion {
                onAction(CategoryDetailsAction.OnLoading(isLoading = false))
            }.catch {
                onAction(CategoryDetailsAction.OnLoading(isLoading = false))

            }.launchIn(viewModelScope)

    }

}
