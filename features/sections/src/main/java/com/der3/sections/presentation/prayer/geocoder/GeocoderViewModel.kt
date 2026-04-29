package com.der3.sections.presentation.prayer.geocoder

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.der3.data_store.api.DataStoreRepository
import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Der3NavigationRoute
import com.der3.screens.Screens
import com.der3.sections.domain.model.GeocoderPayloads
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderAction
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderIntent
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderReducer
import com.der3.sections.presentation.prayer.geocoder.mvi.GeocoderState
import com.der3.sections.presentation.utils.SectionsNavigationKey
import com.der3.utils.CurrentLocationProvider
import com.der3.utils.toJson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GeocoderViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository,
    reducer: GeocoderReducer
) : MviBaseViewModel<GeocoderState, GeocoderAction, GeocoderIntent>(
    initialState = GeocoderState(),
    reducer = reducer
) {

    init {
        dataStoreRepository.mapStyleFlow.onEach { styleValue ->
            GeographyMapStyle.fromValue(styleValue)?.let { style ->
                onAction(GeocoderAction.MapStyleUpdated(style))
            }
        }.launchIn(viewModelScope)

        val savedLat = dataStoreRepository.latitude
        val savedLng = dataStoreRepository.longitude
        if (savedLat != 0.0 || savedLng != 0.0) {
            onAction(GeocoderAction.LocationUpdated(savedLat, savedLng, dataStoreRepository.locationName ?: ""))
        } else {
            handleIntent(GeocoderIntent.UseCurrentLocation)
        }
    }

    override fun handleIntent(intent: GeocoderIntent) {
        when (intent) {
            is GeocoderIntent.MapCameraMoved -> {
                updateLocationName(intent.lat, intent.lng)
            }

            is GeocoderIntent.UseCurrentLocation -> {
                onAction(GeocoderAction.Loading(true))
                CurrentLocationProvider.fetchCurrentLocation(
                    context = context,
                    onSuccess = { location ->
                        // Zoom the map immediately; address is resolved separately
                        onAction(GeocoderAction.LocationUpdated(location.latitude, location.longitude, ""))
                        updateLocationName(location.latitude, location.longitude)
                    },
                    onError = { error ->
                        onAction(GeocoderAction.Error(error))
                    }
                )
            }

            is GeocoderIntent.ChangeMapStyle -> {
                dataStoreRepository.mapStyle = intent.style.value
            }

            is GeocoderIntent.ConfirmLocation -> {
                // In a real app, we would save this to data store
                dataStoreRepository.locationName = viewState.address
                // We should also save lat/lng if we have fields for them
                val payload = GeocoderPayloads(
                    latitude = viewState.latitude,
                    longitude = viewState.longitude,
                    address = viewState.address
                )

                onEffect(
                    MviEffect.Navigate(
                        Screens.BackTo(
                            screen = Der3NavigationRoute.LocationPickerScreen,
                            exclusive = true,
                            payload = mapOf(SectionsNavigationKey.FROM_GEOCODER_SCREEN_KEY to payload.toJson())
                        )
                    )
                )
            }

            is GeocoderIntent.Back -> {
                onEffect(MviEffect.Navigate(Screens.Back()))
            }
        }
    }

    private fun updateLocationName(lat: Double, lng: Double) {
        CurrentLocationProvider.getLocationNameByLatLng(
            context = context,
            latitude = lat,
            longitude = lng,
            onSuccess = { address ->
                onAction(GeocoderAction.LocationUpdated(lat, lng, address))
            },
            onError = {
                onAction(GeocoderAction.LocationUpdated(lat, lng, "Unknown Location"))
            }
        )
    }
}
