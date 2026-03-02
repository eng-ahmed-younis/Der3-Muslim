package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent
import com.der3.ui.models.CategoryUi

@Stable
sealed interface HomeIntent  : MviIntent {

    data object NavigateToDailyNotifications : HomeIntent

    data object NavigateToAllCategories : HomeIntent

    data class NavigateToAzkarDetails(val category: CategoryUi) : HomeIntent
}