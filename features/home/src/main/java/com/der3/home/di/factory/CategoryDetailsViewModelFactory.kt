package com.der3.home.di.factory

import com.der3.shared.params.CategoryDetailsParams
import com.der3.home.presentations.category_details.CategoryDetailsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CategoryDetailsViewModelFactory {
    fun create(params: CategoryDetailsParams): CategoryDetailsViewModel
}