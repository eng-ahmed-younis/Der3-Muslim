package com.der3.sections.presentation.prayer.geocoder.mvi

import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviAction

sealed class GeocoderAction : MviAction {
    data class Loading(val isLoading: Boolean) : GeocoderAction()
    data class LocationUpdated(val lat: Double, val lng: Double, val address: String) : GeocoderAction()
    data class MapStyleUpdated(val style: GeographyMapStyle) : GeocoderAction()
    data class Error(val message: String) : GeocoderAction()
}
