package com.der3.sections.presentation.prayer.location_picker.mvi

import com.der3.mvi.MviIntent

sealed interface LocationPickerIntent : MviIntent {
    object UseCurrentLocation : LocationPickerIntent
    data class SearchCity(val query: String) : LocationPickerIntent
    data class SelectCity(val lat: Double, val lng: Double, val cityName: String) : LocationPickerIntent
    object OpenManualMapPicker : LocationPickerIntent
    object ConfirmLocation : LocationPickerIntent
    object Back : LocationPickerIntent
    data class ChangeMapStyle(val style: com.der3.model.GeographyMapStyle) : LocationPickerIntent
}
