package com.der3.splash.presentation.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject


class IslamicSplashReducer @Inject constructor() : Reducer<IslamicSplashAction, IslamicSplashState> {
    override fun reduce(
        action: IslamicSplashAction,
        state: IslamicSplashState
    ): IslamicSplashState {
        TODO("Not yet implemented")
    }

}