package com.der3.sections.presentation.main_section.mvi

import androidx.compose.runtime.Immutable
import com.der3.mvi.MviState
import com.der3.sections.presentation.main_section.model.Section
import com.der3.ui.models.CategoryUi
import com.der3.utils.CategoryMock

@Immutable
data class MainSectionState(
    val isLoading: Boolean = false,
    val sections: List<Section> = emptyList(),
    val searchQuery: String = "",
    val filteredSections: List<Section> = emptyList()
) : MviState


