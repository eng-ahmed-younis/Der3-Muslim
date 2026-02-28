package com.der3.home.presentations.home_screen.mvi

import androidx.compose.runtime.Stable
import com.der3.data.model.AzkarCategory
import com.der3.mvi.MviAction
import com.der3.ui.models.CategoryUi

@Stable
sealed interface HomeAction  : MviAction {

    data class OnLoading(
        val isLoading: Boolean
    ) : HomeAction

    data class LoadHomeAzkarCategory(
        val category:List<CategoryUi>
    ) : HomeAction
}