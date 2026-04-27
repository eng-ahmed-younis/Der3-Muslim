package com.der3.sections.presentation.prayer.location_picker.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class LocationPickerReducer @Inject constructor() : Reducer<LocationPickerAction, LocationPickerState> {
    override fun reduce(action: LocationPickerAction, state: LocationPickerState): LocationPickerState {
        return when (action) {
            is LocationPickerAction.Loading -> {
                state.copy(isLoading = action.isLoading)
            }

            is LocationPickerAction.LocationUpdated -> {
                state.copy(
                    currentLat = action.lat,
                    currentLng = action.lng,
                    locationName = action.locationName,
                    isLoading = false
                )
            }

            is LocationPickerAction.CurrentLocationRequested -> {
                state.copy(lastLocationUpdateTime = System.currentTimeMillis())
            }

            is LocationPickerAction.SearchResults -> {
                state.copy(
                    searchResults = action.cities,
                    isLoading = false
                )
            }

            is LocationPickerAction.Error -> {
                state.copy(
                    error = action.message,
                    isLoading = false
                )
            }

            is LocationPickerAction.MapStyleUpdated ->{
                state.copy(
                    mapStyle = action.style
                )
            }
        }
    }
}
