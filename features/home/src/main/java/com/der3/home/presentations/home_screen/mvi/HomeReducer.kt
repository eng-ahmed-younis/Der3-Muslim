package com.der3.home.presentations.home_screen.mvi

import com.der3.mvi.Reducer

class HomeReducer : Reducer<HomeAction, HomeState> {
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
        }
    }
}