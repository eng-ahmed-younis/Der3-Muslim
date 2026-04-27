package com.der3.sections.presentation.prayer.location_picker.mvi

import com.der3.model.GeographyMapStyle
import com.der3.mvi.MviState

data class LocationPickerState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val currentLat: Double = 0.0,
    val currentLng: Double = 0.0,
    val locationName: String = "",
    val searchResults: List<CityUi> = emptyList(),
    val suggestedCities: List<CityUi> = listOf(
        CityUi("الرياض", "المملكة العربية السعودية", 24.7136, 46.6753),
        CityUi("مكة المكرمة", "المملكة العربية السعودية", 21.3891, 39.8579),
        CityUi("المدينة المنورة", "المملكة العربية السعودية", 24.4672, 39.6068),
        CityUi("القاهرة", "جمهورية مصر العربية", 30.0444, 31.2357)
    ),
    val mapStyle: GeographyMapStyle = GeographyMapStyle.OsmBright,
    val lastLocationUpdateTime: Long? = null,
    val error: String? = null
) : MviState
