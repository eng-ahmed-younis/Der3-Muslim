package com.der3.home.presentations.home_screen

import com.der3.home.presentations.home_screen.mvi.HomeAction
import com.der3.home.presentations.home_screen.mvi.HomeIntent
import com.der3.home.presentations.home_screen.mvi.HomeReducer
import com.der3.home.presentations.home_screen.mvi.HomeState
import com.der3.mvi.MviBaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : MviBaseViewModel<HomeState, HomeAction, HomeIntent>(
    initialState = HomeState(),
    reducer = HomeReducer()
) {
    override fun handleIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}