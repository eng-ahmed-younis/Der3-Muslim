package com.der3.home.presentations.all_categories.mvi

import androidx.compose.runtime.Stable
import com.der3.mvi.MviIntent

@Stable
sealed interface AllCategoryIntent : MviIntent {

    data class UpdateSearchQuery(val query: String) : AllCategoryIntent
}