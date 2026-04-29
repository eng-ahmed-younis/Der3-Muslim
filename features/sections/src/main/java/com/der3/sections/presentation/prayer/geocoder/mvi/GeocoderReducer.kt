package com.der3.sections.presentation.prayer.geocoder.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class GeocoderReducer @Inject constructor() : Reducer<GeocoderAction, GeocoderState> {
    override fun reduce(action: GeocoderAction, state: GeocoderState): GeocoderState {
        return when (action) {
            is GeocoderAction.Loading -> state.copy(isLoading = action.isLoading)
            is GeocoderAction.LocationUpdated -> state.copy(
                latitude = action.lat,
                longitude = action.lng,
                address = action.address,
                isLoading = false
            )
            is GeocoderAction.MapStyleUpdated -> state.copy(mapStyle = action.style)
            is GeocoderAction.Error -> state.copy(error = action.message, isLoading = false)
        }
    }
}
