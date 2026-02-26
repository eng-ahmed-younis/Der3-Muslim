package com.der3.on_boarding.presentation.screens.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class OnBoardingReducer @Inject constructor() :
    Reducer<OnBoardingAction, OnBoardingState> {

    override fun reduce(
        action: OnBoardingAction,
        state: OnBoardingState
    ): OnBoardingState {

        return when (action) {

            is OnBoardingAction.NextPage -> {
                val next = (state.currentPage + 1)
                    .coerceAtMost(state.totalPages - 1)

                state.copy(currentPage = next)
            }

            is OnBoardingAction.PreviousPage -> {
                val prev = (state.currentPage - 1)
                    .coerceAtLeast(0)

                state.copy(currentPage = prev)
            }


            is OnBoardingAction.LoadOnBoarding -> {
                state
            }
        }
    }
}