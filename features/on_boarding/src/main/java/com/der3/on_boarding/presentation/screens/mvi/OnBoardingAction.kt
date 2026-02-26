package com.der3.on_boarding.presentation.screens.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviAction

@Stable
sealed class OnBoardingAction : MviAction {
    data class LoadOnBoarding(val page: Int): OnBoardingAction()
    data class NextPage(val page: Int): OnBoardingAction()
    data class PreviousPage(val page: Int): OnBoardingAction()
}