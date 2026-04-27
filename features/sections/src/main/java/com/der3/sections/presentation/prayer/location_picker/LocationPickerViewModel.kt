package com.der3.sections.presentation.prayer.location_picker

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerAction
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerIntent
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerReducer
import com.der3.sections.presentation.prayer.location_picker.mvi.LocationPickerState
import com.der3.utils.CurrentLocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationPickerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository,
    reducer: LocationPickerReducer
) : MviBaseViewModel<LocationPickerState, LocationPickerAction, LocationPickerIntent>(
    initialState = LocationPickerState(),
    reducer = reducer
) {

    init {
        dataStoreRepository.mapStyleFlow.onEach { styleValue ->
            GeographyMapStyle.fromValue(styleValue)?.let { style ->
                onAction(LocationPickerAction.MapStyleUpdated(style))
            }
        }.launchIn(viewModelScope)
    }

    override fun handleIntent(intent: LocationPickerIntent) {
        when (intent) {
            is LocationPickerIntent.UseCurrentLocation -> {
                onAction(LocationPickerAction.CurrentLocationRequested)
                onAction(LocationPickerAction.Loading(true))
                CurrentLocationProvider.fetchCurrentLocation(
                    context = context,
                    onSuccess = { location ->
                        CurrentLocationProvider.getLocationNameByLatLng(
                            context = context,
                            latitude = location.latitude,
                            longitude = location.longitude,
                            onSuccess = { cityName ->
                                onAction(
                                    LocationPickerAction.LocationUpdated(
                                        lat = location.latitude,
                                        lng = location.longitude,
                                        locationName = cityName
                                    )
                                )
                            },
                            onError = {
                                onAction(
                                    LocationPickerAction.LocationUpdated(
                                        lat = location.latitude,
                                        lng = location.longitude,
                                        locationName = "Unknown Location"
                                    )
                                )
                            }
                        )
                    },
                    onError = { error ->
                        onAction(LocationPickerAction.Error(error))
                    }
                )
            }

            is LocationPickerIntent.SearchCity -> {
                // TODO: Implement city search
            }

            is LocationPickerIntent.SelectCity -> {
                onAction(
                    LocationPickerAction.LocationUpdated(
                        intent.lat,
                        intent.lng,
                        intent.cityName
                    )
                )
                onEffect(MviEffect.Navigate(Screens.Back()))
            }

            is LocationPickerIntent.OpenManualMapPicker -> {
                // TODO: Navigate to map picker
            }

            is LocationPickerIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }

            is LocationPickerIntent.ChangeMapStyle -> {
                dataStoreRepository.mapStyle = intent.style.value
            }
        }
    }
}
