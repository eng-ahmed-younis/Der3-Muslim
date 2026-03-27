package com.der3.sections.presentation.main_section.mvi

import com.der3.mvi.MviAction
import com.der3.sections.presentation.main_section.model.Section
import com.der3.ui.models.CategoryUi

sealed interface MainSectionAction : MviAction {
    data class OnSectionsLoaded(val sections: List<Section>) : MainSectionAction
    data class OnSearchQueryUpdated(val query: String) : MainSectionAction
    data class OnLoading(val isLoading: Boolean) : MainSectionAction
}
