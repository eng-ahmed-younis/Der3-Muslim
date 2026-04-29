package com.der3.sections.presentation.prayer.geocoder.mvi

import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviState

data class GeocoderState(
    val isLoading: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val mapStyle: GeographyMapStyle = GeographyMapStyle.OsmBright,
    val error: String? = null
) : MviState
