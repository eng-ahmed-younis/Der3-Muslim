package com.der3.on_boarding.presentation.screens

import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingAction
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingIntent
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingReducer
import com.der3.on_boarding.presentation.screens.mvi.OnBoardingState
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : MviBaseViewModel<OnBoardingState, OnBoardingAction, OnBoardingIntent>(
    initialState = OnBoardingState(),
    reducer = OnBoardingReducer()
) {
    override fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {

            is OnBoardingIntent.NextPage -> {
                onAction(OnBoardingAction.NextPage(intent.page))
            }

            is OnBoardingIntent.PreviousPage ->
                onAction(OnBoardingAction.PreviousPage(intent.page))

            is OnBoardingIntent.SkipOnBoarding -> {
                saveOnBoardingState()
                onEffect(
                    MviEffect.Navigate(
                        screen = Screens.Replace(
                            oldScreen = Der3NavigationRoute.OnboardingScreen::class,
                            newScreen = Der3NavigationRoute.HomeScreen
                        )
                    )
                )
            }

            is OnBoardingIntent.CompleteOnBoarding -> {
                saveOnBoardingState()
                onEffect(
                    MviEffect.Navigate(
                        screen = Screens.Replace(
                            oldScreen = Der3NavigationRoute.OnboardingScreen::class,
                            newScreen = Der3NavigationRoute.HomeScreen
                        )
                    )
                )
            }
        }
    }

    private fun saveOnBoardingState() {
        viewModelScope.launch {
            dataStoreRepository.hasCompletedOnboarding = true
        }
    }


}