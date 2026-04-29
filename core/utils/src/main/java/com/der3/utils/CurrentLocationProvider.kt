package com.der3.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.Locale

/**
 * Helper object to manage location permissions and fetch the current location.
 */
object CurrentLocationProvider {
    private const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    fun hasLocationPermission(context: Context): Boolean {
        val fineLocation =
            ContextCompat.checkSelfPermission(context, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        val coarseLocation =
            ContextCompat.checkSelfPermission(context, COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        return fineLocation || coarseLocation
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!hasLocationPermission(context)) {
            onError("Location permission not granted")
            return
        }

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->

            if (location != null) {
                onSuccess(location)
            } else {
                onError("Location is null. Make sure GPS/location is enabled.")
            }

        }.addOnFailureListener { exception ->
            onError(exception.message ?: "Failed to get location")
        }
    }

    fun getLocationNameByLatLng(
        context: Context,
        latitude: Double,
        longitude: Double,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val arabicLocale = Locale.Builder()
            .setLanguage("ar")
            .build()

        try {
            val geocoder = Geocoder(context, arabicLocale)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) { addresses ->
                    val address = addresses.firstOrNull()

                    if (address != null) {
                        onSuccess(addressToName(address))
                    } else {
                        onError("No address found")
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                val address = addresses?.firstOrNull()

                if (address != null) {
                    onSuccess(addressToName(address))
                } else {
                    onError("No address found")
                }
            }

        } catch (e: Exception) {
            onError(e.message ?: "Failed to get location name")
        }
    }

    private fun addressToName(address: Address): String {
        return address.getAddressLine(0)
            ?: listOfNotNull(
                address.locality,        // city
                address.adminArea,       // state/governorate
                address.countryName      // country
            ).joinToString(", ").take(2)
    }
}