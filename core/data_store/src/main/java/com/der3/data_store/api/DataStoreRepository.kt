package com.der3.data_store.api

interface DataStoreRepository {

    var hasCompletedOnboarding: Boolean
    var zekrScreenDetailsFontSize: Int
    var masbahaDataVersion: Int
    var is24HourFormat: Boolean
    var latitude: Double
    var longitude: Double
    var locationName: String?
}