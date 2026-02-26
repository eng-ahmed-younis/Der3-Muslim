package com.der3.on_boarding.presentation.screens.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface OnBoardingIntent: MviIntent {

    data class NextPage(val page: Int): OnBoardingIntent
    data class PreviousPage(val page: Int): OnBoardingIntent
    data class SkipOnBoarding(val page: Int): OnBoardingIntent
    data class CompleteOnBoarding(val page: Int): OnBoardingIntent
}