package com.der3.sections.domain.model


data class PrayerRequestParams(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val city: String? = null,
    val country: String? = null,
    val state: String? = null,
    val address: String? = null,
    val date: String = "today",
    val method: Int = 2,
    val school: Int = 0,
    val tune: String? = null,
    val timezone: String? = null,
    val iso8601: Boolean = false,
    val shafaq: String? = null,
    val midnightMode: Int = 0,
    val latitudeAdjustment: Int = 3,
    val calendarMethod: String = "HJCoSA"
) {
    fun validate() {
        require(latitude != null || city != null || address != null) {
            "Either latitude/longitude, city, or address must be provided"
        }
    }
}

class PrayerRequestBuilder {
    private var params = PrayerRequestParams()

    fun withCoordinates(lat: Double, lng: Double) = apply {
        params = params.copy(latitude = lat, longitude = lng)
    }

    fun withCity(city: String, country: String, state: String? = null) = apply {
        params = params.copy(city = city, country = country, state = state)
    }

    fun withAddress(address: String) = apply {
        params = params.copy(address = address)
    }

    fun withDate(date: String) = apply {
        params = params.copy(date = date)
    }

    fun withMethod(method: Int) = apply {
        params = params.copy(method = method)
    }

    fun withSchool(school: Int) = apply {
        params = params.copy(school = school)
    }

    fun withTune(tune: String) = apply {
        params = params.copy(tune = tune)
    }

    fun withTimezone(timezone: String) = apply {
        params = params.copy(timezone = timezone)
    }

    fun withIso8601(iso8601: Boolean) = apply {
        params = params.copy(iso8601 = iso8601)
    }

    fun withShafaq(shafaq: String) = apply {
        params = params.copy(shafaq = shafaq)
    }

    fun withMidnightMode(mode: Int) = apply {
        params = params.copy(midnightMode = mode)
    }

    fun withLatitudeAdjustment(adjustment: Int) = apply {
        params = params.copy(latitudeAdjustment = adjustment)
    }

    fun withCalendarMethod(method: String) = apply {
        params = params.copy(calendarMethod = method)
    }

    fun build(): PrayerRequestParams {
        params.validate()
        return params
    }
}