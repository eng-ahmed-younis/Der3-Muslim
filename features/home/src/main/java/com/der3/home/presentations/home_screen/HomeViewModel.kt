package com.der3.home.presentations.home_screen

import androidx.lifecycle.viewModelScope
import com.der3.shared.domain.use_case.GetAzkarCategoriesUseCase
import com.der3.home.data.mappers.toUiCategories
import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.model.NotificationType
import com.der3.model.UiText
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.*
import com.der3.screens.Der3NavigationRoute
import com.der3.shared.domain.use_case.notification.GetNotificationByTypeUseCase
import com.der3.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAzkarCategoriesUseCase,
    private val getNotificationByTypeUseCase: GetNotificationByTypeUseCase,
    reducer: HomeReducer
) : MviBaseViewModel<HomeState, HomeAction, HomeIntent>(
    initialState = HomeState(),
    reducer = reducer
) {
    init {
        getAllAzkarCategories()
        getDailyNotification()
    }

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.NavigateToDailyNotifications -> {
                onEffect(Navigate(screen = Der3NavigationRoute.DailyNotificationsScreen))
            }

            HomeIntent.NavigateToAllCategories -> {
                onEffect(Navigate(screen = Der3NavigationRoute.AllAzkarCategoriesScreen))

            }

            is HomeIntent.NavigateToAzkarDetails -> {
                onEffect(
                    Navigate(
                        screen = Der3NavigationRoute.CategoryDetailsScreen(
                            categoryId = intent.category.id,
                            categoryTitle = intent.category.title,
                            categorySubtitle = intent.category.subtitle,
                            categoryCount = intent.category.count
                        )
                    )
                )
            }

            HomeIntent.DismissError -> {
                onAction(HomeAction.ClearError)
            }

            HomeIntent.Retry -> {
                getAllAzkarCategories()
                getDailyNotification()
            }

            HomeIntent.NavigateToNotifications -> {
                onEffect(Navigate(screen = Der3NavigationRoute.NotificationScreen()))
            }
        }
    }

    private fun getDailyNotification() {
        getNotificationByTypeUseCase.invoke(type = NotificationType.DAILY)
            .onStart { onAction(HomeAction.OnLoading(true)) }
            .onEach { notification ->
                onAction(
                    HomeAction.LoadDailyNotification(
                        title = notification?.title,
                        desc = notification?.body
                    )
                )
            }
            .onCompletion {
                onAction(HomeAction.OnLoading(false))
            }
            .catch { error ->
                onEffect(MviEffect.OnErrorDialog(error = UiText.ResourceError(messageId = R.string.error_dialog_message)))
            }.launchIn(viewModelScope)
    }

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
