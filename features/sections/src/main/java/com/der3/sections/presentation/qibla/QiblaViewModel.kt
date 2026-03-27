package com.der3.sections.presentation.qibla

import com.der3.sections.presentation.qibla.mvi.QiblaAction
import com.der3.sections.presentation.qibla.mvi.QiblaIntent
import com.der3.sections.presentation.qibla.mvi.QiblaReducer
import com.der3.sections.presentation.qibla.mvi.QiblaState
import android.location.Location
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.viewModelScope
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import android.hardware.GeomagneticField
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.util.Locale

@HiltViewModel
class QiblaViewModel @Inject constructor(
    reducer: QiblaReducer,
    @ApplicationContext private val context: Context
) : MviBaseViewModel<QiblaState, QiblaAction, QiblaIntent>(
    initialState = QiblaState(),
    reducer = reducer
) {
    private var addressJob: Job? = null
    private var currentDeclination: Float = 0f

    public override fun handleIntent(intent: QiblaIntent) {
        when (intent) {
            is QiblaIntent.StartCompass -> {
                onAction(QiblaAction.OnLoading(true))
            }
            is QiblaIntent.StopCompass -> {
                onAction(QiblaAction.OnLoading(false))
            }
            is QiblaIntent.OnBackClick -> onEffect(MviEffect.Navigate(Screens.Back()))
            is QiblaIntent.Retry -> {
                onAction(QiblaAction.OnLoading(true))
                // Retry logic here if needed, or just clear loading
            }
            is QiblaIntent.OnLocationChanged -> updateLocationInfo(intent.latitude, intent.longitude)
            is QiblaIntent.OnDirectionUpdated -> {
                // Adjust compass rotation with magnetic declination
                val adjustedCompassRotation = intent.compassRotation + currentDeclination
                onAction(QiblaAction.OnDirectionUpdated(intent.qiblaDirection, adjustedCompassRotation))
            }
        }
    }

    private fun updateLocationInfo(latitude: Double, longitude: Double) {
        // Calculate Distance immediately
        val distanceResult = FloatArray(1)
        Location.distanceBetween(latitude, longitude, 21.422487, 39.826206, distanceResult)
        val distanceKm = distanceResult[0] / 1000.0

        // Calculate Magnetic Declination
        val geomagneticField = GeomagneticField(
            latitude.toFloat(),
            longitude.toFloat(),
            0f,
            System.currentTimeMillis()
        )
        currentDeclination = geomagneticField.declination

        // Update UI with distance and stop loading
        onAction(QiblaAction.OnLocationUpdated(viewState.currentLocationName, distanceKm))
        onAction(QiblaAction.OnLoading(false))

        // Fetch address in background without blocking
        addressJob?.cancel()
        addressJob = viewModelScope.launch {
            val locationName = getAddress(latitude, longitude)
            if (locationName != "موقع مجهول" && locationName != "موقع غير معروف") {
                onAction(QiblaAction.OnLocationUpdated(locationName, distanceKm))
            }
        }
    }

    private suspend fun getAddress(latitude: Double, longitude: Double): String = withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale("ar"))
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val city = address.locality ?: address.subAdminArea ?: ""
                val country = address.countryName ?: ""
                if (city.isNotEmpty() && country.isNotEmpty()) "$city، $country"
                else if (city.isNotEmpty()) city
                else country
            } else {
                "موقع غير معروف"
            }
        } catch (e: Exception) {
            "موقع مجهول"
        }
    }
}
