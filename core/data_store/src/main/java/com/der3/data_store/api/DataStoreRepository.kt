package com.der3.data_store.api

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    var hasCompletedOnboarding: Boolean
    var zekrScreenDetailsFontSize: Int
    var masbahaDataVersion: Int
    var is24HourFormat: Boolean
    var latitude: Double
    var longitude: Double
    var locationName: String?
    var appStyle: String
    val appStyleFlow: Flow<String>
}
