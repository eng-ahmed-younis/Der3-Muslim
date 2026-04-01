package com.der3.home.presentations.home_screen.mvi

import com.der3.mvi.Reducer
import jakarta.inject.Inject

class HomeReducer @Inject constructor() : Reducer<HomeAction, HomeState> {
    override fun reduce(
        action: HomeAction,
        state: HomeState
    ): HomeState {
        return when (action){

            is HomeAction.OnLoading -> {
                state.copy(
                    isLoading = action.isLoading
                )
            }

            is HomeAction.LoadHomeAzkarCategory -> {
                state.copy(
                    homeAzkarCategory = action.category
                )
            }

            is HomeAction.LoadDailyNotification -> {
                state.copy(
                    dailyNotificationTitle = action.title,
                    dailyNotificationDesc = action.desc
                )
            }

            HomeAction.ClearError -> {
                state.copy(
                    error = null
                )
            }
        }
    }
}