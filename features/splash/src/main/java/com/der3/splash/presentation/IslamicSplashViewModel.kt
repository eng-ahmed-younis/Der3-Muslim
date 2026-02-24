package com.der3.splash.presentation

import com.der3.mvi.MviBaseViewModel
import com.der3.splash.presentation.mvi.IslamicSplashAction
import com.der3.splash.presentation.mvi.IslamicSplashIntent
import com.der3.splash.presentation.mvi.IslamicSplashReducer
import com.der3.splash.presentation.mvi.IslamicSplashState
import javax.inject.Inject

class IslamicSplashViewModel @Inject constructor() : MviBaseViewModel<IslamicSplashState, IslamicSplashAction , IslamicSplashIntent> (
    initialState = IslamicSplashState(),
    reducer = IslamicSplashReducer()
){
    override fun handleIntent(intent: IslamicSplashIntent) {
        TODO("Not yet implemented")
    }

}