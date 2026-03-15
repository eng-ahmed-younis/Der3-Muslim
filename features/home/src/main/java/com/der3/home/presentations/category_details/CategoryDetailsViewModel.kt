package com.der3.home.presentations.category_details

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.der3.home.data.mappers.toZekrUiModels
import com.der3.home.di.factory.CategoryDetailsViewModelFactory
import com.der3.home.domain.model.ZekrUiModel
import com.der3.home.presentations.category_details.mvi.CategoryDetailsAction
import com.der3.home.presentations.category_details.mvi.CategoryDetailsIntent
import com.der3.home.presentations.category_details.mvi.CategoryDetailsReducer
import com.der3.home.presentations.category_details.mvi.CategoryDetailsState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.Navigate
import com.der3.screens.Der3NavigationRoute.ZekrDetailsScreen
import com.der3.screens.Screens.Back
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.use_case.fav.AddToFavoriteUseCase
import com.der3.shared.domain.use_case.fav.GetAllFavouritesUsecase
import com.der3.shared.domain.use_case.GetAzkarCategoryByIdUseCase
import com.der3.shared.domain.use_case.fav.RemoveFromFavoriteUseCase
import com.der3.shared.params.CategoryDetailsParams
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = CategoryDetailsViewModelFactory::class)
class CategoryDetailsViewModel @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted private val params: CategoryDetailsParams,
    private val getAzkarCategoryByIdUseCase: GetAzkarCategoryByIdUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val getAllFavouritesUsecase: GetAllFavouritesUsecase
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
        when (intent) {
            is CategoryDetailsIntent.OnBackClick -> {
                onEffect(Navigate(Back()))
            }

            is CategoryDetailsIntent.OnZekrClick -> {
                onEffect(
                    Navigate(
                        screen = ZekrDetailsScreen(
                            categoryId = params.categoryId,
                            zekrId = intent.zekrId
                        )
                    )
                )
            }

            // is AzkarDetailsIntent.OnShareClick -> onAction(AzkarDetailsAction.OnShareClick(intent.zekr))
            is CategoryDetailsIntent.OnBookmarkClick -> {}
            is CategoryDetailsIntent.OnFavoriteClick -> {
                toggleFavoriteStatus(intent.zekr)
            }
            is CategoryDetailsIntent.OnPlayClick -> {}
            is CategoryDetailsIntent.OnShareClick -> {}
        }

    }


    private fun getCategoryDetails(
        categoryId: Int
    ) {
        getAzkarCategoryByIdUseCase.invoke(id = categoryId)
            .onStart { onAction(CategoryDetailsAction.OnLoading(isLoading = true)) }
            .combine(getAllFavouritesUsecase.invoke()) { category, favorites ->
                val azkars = category?.toZekrUiModels() ?: emptyList()
                val favoriteIds =
                    favorites.filter { it.categoryId == categoryId }.map { it.id }.toSet()
                azkars.map { zekr ->
                    zekr.copy(isFavorite = favoriteIds.contains(zekr.id))
                }
            }
            .onEach { azkars ->
                onAction(
                    CategoryDetailsAction.OnCategoryLoaded(
                        azkars = azkars
                    )
                )
            }.catch { exception ->
                onEffect(
                    MviEffect.OnErrorDialog(
                        error = UiText.DynamicError(
                            exception.message ?: "Unknown Error"
                        )
                    )
                )
            }.onCompletion {
                onAction(CategoryDetailsAction.OnLoading(isLoading = false))
            }.launchIn(viewModelScope)

    }


    private fun toggleFavoriteStatus(zekr: ZekrUiModel) {
        viewModelScope.launch {
            if (zekr.isFavorite) {
                removeFavoriteUseCase.invoke(zekr.id)
            } else {
                addToFavoriteUseCase.invoke(
                    FavoriteEntity(
                        id = zekr.id,
                        text = zekr.text,
                        audioPath = zekr.audioPath,
                        repeatCount = zekr.repeatCount,
                        categoryName = viewState.categoryTitle,
                        categoryId = params.categoryId
                    )
                )
            }
            // Update the list locally for immediate UI feedback
            val updatedList = viewState.azkarItems.map {
                if (it.id == zekr.id) it.copy(isFavorite = !zekr.isFavorite) else it
            }
            onAction(CategoryDetailsAction.OnCategoryLoaded(updatedList))
        }
    }

}
