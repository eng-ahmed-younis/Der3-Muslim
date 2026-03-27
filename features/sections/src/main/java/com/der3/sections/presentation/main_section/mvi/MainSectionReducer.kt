package com.der3.sections.presentation.main_section.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class MainSectionReducer @Inject constructor() : Reducer<MainSectionAction, MainSectionState> {
    override fun reduce(action: MainSectionAction, state: MainSectionState): MainSectionState {
        return when (action) {
            is MainSectionAction.OnLoading -> state.copy(isLoading = action.isLoading)
            is MainSectionAction.OnSectionsLoaded -> state.copy(
                sections = action.sections,
                filteredSections = if (state.searchQuery.isEmpty()) action.sections else action.sections.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true)
                },
                isLoading = false
            )
            is MainSectionAction.OnSearchQueryUpdated -> {
                val filtered = if (action.query.isEmpty()) {
                    state.sections
                } else {
                    state.sections.filter {
                        it.title.contains(action.query, ignoreCase = true)
                    }
                }
                state.copy(searchQuery = action.query, filteredSections = filtered)
            }
        }
    }
}
