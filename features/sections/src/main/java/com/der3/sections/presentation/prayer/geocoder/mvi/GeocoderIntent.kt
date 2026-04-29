package com.der3.sections.presentation.prayer.geocoder.mvi

import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviIntent

sealed class GeocoderIntent : MviIntent {
    data class MapCameraMoved(val lat: Double, val lng: Double) : GeocoderIntent()
    data class ChangeMapStyle(val style: GeographyMapStyle) : GeocoderIntent()
    object UseCurrentLocation : GeocoderIntent()
    object ConfirmLocation : GeocoderIntent()
    object Back : GeocoderIntent()
}
