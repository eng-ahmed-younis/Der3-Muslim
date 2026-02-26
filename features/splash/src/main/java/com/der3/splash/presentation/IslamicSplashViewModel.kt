package com.der3.splash.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.splash.presentation.mvi.IslamicSplashAction
import com.der3.splash.presentation.mvi.IslamicSplashIntent
import com.der3.splash.presentation.mvi.IslamicSplashReducer
import com.der3.splash.presentation.mvi.IslamicSplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IslamicSplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : MviBaseViewModel<IslamicSplashState, IslamicSplashAction , IslamicSplashIntent> (
    initialState = IslamicSplashState(),
    reducer = IslamicSplashReducer()
){

    init {
        startSplashTimer()
    }

    override fun handleIntent(intent: IslamicSplashIntent) {}


    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(1000)
            onSplashFinished()
        }
    }

    private fun onSplashFinished() {
        when(dataStoreRepository.hasCompletedOnboarding){
            true ->{
                Log.i("IslamicSplashViewModel","true")
                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.HomeScreen))
            }
            false ->{
                Log.i("IslamicSplashViewModel","false")

                onEffect(MviEffect.Navigate(screen = Der3NavigationRoute.OnboardingScreen))
            }
        }
    }

}