package com.der3.sections.presentation.main_section.mvi

import com.der3.mvi.MviIntent
import com.der3.sections.presentation.main_section.model.SectionType

sealed interface MainSectionIntent : MviIntent {
    data object LoadSections : MainSectionIntent
    data object Retry : MainSectionIntent
    data class UpdateSearchQuery(val query: String) : MainSectionIntent
    data class OnSectionClick(
        val id: Int,
        val type: SectionType
    ) : MainSectionIntent

}
