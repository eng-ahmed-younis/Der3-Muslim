package com.der3.on_boarding.presentation.screens

import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingAction
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingIntent
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingReducer
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingState
import com.der3.screens.Der3NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : MviBaseViewModel<OnBoardingState, OnBoardingAction, OnBoardingIntent>(
        initialState = OnBoardingState(),
        reducer = OnBoardingReducer()
) {
    override fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {

            is OnBoardingIntent.NextPage ->{
                onAction(OnBoardingAction.NextPage(intent.page))
            }

            is OnBoardingIntent.PreviousPage ->
                onAction(OnBoardingAction.PreviousPage(intent.page))

            is OnBoardingIntent.SkipOnBoarding ->
                onAction(OnBoardingAction.SkipOnBoarding(intent.page))

            is OnBoardingIntent.CompleteOnBoarding ->{
                onAction(OnBoardingAction.CompleteOnBoarding(intent.page))
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.MainScreen))
            }

        }
    }


}