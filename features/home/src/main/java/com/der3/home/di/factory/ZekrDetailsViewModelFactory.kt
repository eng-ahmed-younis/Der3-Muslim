package com.der3.home.di.factory

import com.der3.data.params.ZekrDetailsParams
import com.der3.home.presentations.zekr_details.ZekrDetailsViewModel
import dagger.assisted.AssistedFactory


@AssistedFactory
interface ZekrDetailsViewModelFactory {
    fun create(params: ZekrDetailsParams): ZekrDetailsViewModel
}