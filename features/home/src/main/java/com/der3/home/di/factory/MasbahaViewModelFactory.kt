package com.der3.home.di.factory

import com.der3.home.presentations.masbaha.MasbahaViewModel
import com.der3.shared.params.MasbahaParams
import dagger.assisted.AssistedFactory


@AssistedFactory
interface MasbahaViewModelFactory {
    fun create(params: MasbahaParams): MasbahaViewModel
}