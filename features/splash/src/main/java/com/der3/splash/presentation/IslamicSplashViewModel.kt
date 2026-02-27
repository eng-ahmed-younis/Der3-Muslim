package com.der3.splash.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.der3.data.repo.AzkarRepository
import com.der3.data_store.api.DataStoreRepository
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
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
    private val dataStoreRepository: DataStoreRepository,
    private val azkarRepository: AzkarRepository
) : MviBaseViewModel<IslamicSplashState, IslamicSplashAction, IslamicSplashIntent>(
    initialState = IslamicSplashState(),
    reducer = IslamicSplashReducer()
) {

    init {
        startSplashTimer()
    }

    override fun handleIntent(intent: IslamicSplashIntent) {}


    private fun startSplashTimer() {
        viewModelScope.launch {
            //  delay(1000)
            //  onSplashFinished()
            loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                // Load JSON and wait minimum 1 second both together
                val loadJob = launch { azkarRepository.getAllCategories() }
                val timerJob = launch { delay(1000) }

                // Wait for BOTH to finish
                loadJob.join()
                timerJob.join()

                onSplashFinished()

            } catch (e: Exception) {
                Log.e("IslamicSplashViewModel", "Error loading data: ${e.message}")
                // Still navigate even if error
                delay(1000)
                onSplashFinished()
            }
        }
    }

    private fun onSplashFinished() {
        when (dataStoreRepository.hasCompletedOnboarding) {
            true -> {
                Log.i("IslamicSplashViewModel", "true")
                onEffect(
                    MviEffect.Navigate(
                        screen = Screens.Replace(
                            oldScreen = Der3NavigationRoute.SplashScreen::class,
                            newScreen = Der3NavigationRoute.HomeScreen
                        )
                    )
                )
            }

            false -> {
                Log.i("IslamicSplashViewModel", "false")
                onEffect(
                    MviEffect.Navigate(
                        screen = Screens.Replace(
                            oldScreen = Der3NavigationRoute.SplashScreen::class,
                            newScreen = Der3NavigationRoute.OnboardingScreen
                        )
                    )
                )
            }
        }
    }

}