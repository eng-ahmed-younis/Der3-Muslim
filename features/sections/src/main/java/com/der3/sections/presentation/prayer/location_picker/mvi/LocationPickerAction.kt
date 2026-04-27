package com.der3.sections.presentation.prayer.location_picker.mvi

import com.der3.mvi.MviAction

sealed interface LocationPickerAction : MviAction {
    data class Loading(val isLoading: Boolean) : LocationPickerAction
    data class LocationUpdated(val lat: Double, val lng: Double, val locationName: String) : LocationPickerAction
    data class SearchResults(val cities: List<CityUi>) : LocationPickerAction
    data class Error(val message: String?) : LocationPickerAction
    data class MapStyleUpdated(val style: com.der3.model.GeographyMapStyle) : LocationPickerAction
    object CurrentLocationRequested : LocationPickerAction
}

data class CityUi(
    val name: String,
    val country: String,
    val lat: Double,
    val lng: Double
)
