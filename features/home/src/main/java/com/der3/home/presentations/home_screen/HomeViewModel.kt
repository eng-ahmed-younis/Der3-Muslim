package com.der3.home.presentations.home_screen

import androidx.lifecycle.viewModelScope
import com.der3.data.mappers.toUiCategories
import com.der3.data.use_case.GetAzkarCategoriesUseCase
import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.*
import com.der3.screens.Der3NavigationRoute
import com.der3.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAzkarCategoriesUseCase
) : MviBaseViewModel<HomeState, HomeAction, HomeIntent>(
    initialState = HomeState(),
    reducer = HomeReducer()
) {
    init {
        getAllAzkarCategories()
    }

    override fun handleIntent(intent: HomeIntent) {
        when(intent){
            is HomeIntent.NavigateToDailyNotifications -> {
                onEffect(Navigate(screen = Der3NavigationRoute.DailyNotificationsScreen))
            }

            HomeIntent.NavigateToAllCategories -> {
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.AllCategoriesScreen))

            }
        }
    }

 /*   private fun getAllAzkarCategories() {
        getAllCategoriesUseCase.invoke()
            .onStart {
                onAction(HomeAction.OnLoading(true))
            }.onEach { categories ->
                onAction(HomeAction.LoadHomeAzkarCategory(category = categories))
            }.onCompletion {
                onAction(HomeAction.OnLoading(false))
            }.catch {

            }.launchIn(viewModelScope)
    }*/

    private fun getAllAzkarCategories() {
        getAllCategoriesUseCase.invoke()
            .onStart {
                onAction(HomeAction.OnLoading(true))
            }
            .map { categories ->
                categories.toUiCategories()  // ← convert here
            }
            .onEach { uiCategories ->
                onAction(HomeAction.LoadHomeAzkarCategory(category = uiCategories))
            }
            .onCompletion {
                onAction(HomeAction.OnLoading(false))
            }
            .catch {
                onEffect(MviEffect.OnErrorDialog(error = UiText.ResourceError(messageId = R.string.error_dialog_message)))
                onAction(HomeAction.OnLoading(false))
            }
            .launchIn(viewModelScope)
    }
}