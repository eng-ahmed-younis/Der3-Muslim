package com.der3.data_store.impl

import com.der3.data_store.api.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class DataStoreRepositoryImpl(private val dataStoreService: DataStoreService) : DataStoreRepository {

    override var hasCompletedOnboarding: Boolean
        get() = runBlocking (Dispatchers.IO){
            dataStoreService.get<Boolean>(DataStoreKeys.ONBOARDING_KEY, false).first()
        }
        set(value) {
            runBlocking (Dispatchers.IO){
                dataStoreService.set(DataStoreKeys.ONBOARDING_KEY, value)
            }
        }
    override var zekrScreenDetailsFontSize: Int
        get() = runBlocking {
            dataStoreService.get<Int>(DataStoreKeys.ZEKR_DETAILS_SCREEN_FONT_SIZE, 24).first()

        }
        set(value) {
            runBlocking (Dispatchers.IO){
                dataStoreService.set(DataStoreKeys.ZEKR_DETAILS_SCREEN_FONT_SIZE, value)
            }
        }
    override var masbahaDataVersion: Int
        get() = runBlocking (Dispatchers.IO) {
            dataStoreService.get<Int>(DataStoreKeys.MASBAHA_DATA_VERSION, 0).first()
        }
        set(value) {
            runBlocking (Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.MASBAHA_DATA_VERSION, value)
            }
        }

    override var is24HourFormat: Boolean
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<Boolean>(DataStoreKeys.IS_24_HOUR_FORMAT, false).first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.IS_24_HOUR_FORMAT, value)
            }
        }

    override var latitude: Double
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<Double>(DataStoreKeys.SAVED_LOCATION_LATITUDE, 21.4225).first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.SAVED_LOCATION_LATITUDE, value)
            }
        }

    override var longitude: Double
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<Double>(DataStoreKeys.SAVED_LOCATION_LONGITUDE, 39.8262).first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.SAVED_LOCATION_LONGITUDE, value)
            }
        }

    override var locationName: String?
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<String>(DataStoreKeys.LOCATION_NAME, "مكة المكرمة").first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.LOCATION_NAME, value ?: "مكة المكرمة")
            }
        }


    override var appStyle: String
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<String>(DataStoreKeys.APP_STYLE, "system").first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.APP_STYLE, value ?: "system")
            }
        }

    override val appStyleFlow: Flow<String> = dataStoreService[DataStoreKeys.APP_STYLE, "system"]

    override var playbackSpeed: Float
        get() = runBlocking(Dispatchers.IO) {
            dataStoreService.get<Float>(DataStoreKeys.PLAYBACK_SPEED, 1.0f).first()
        }
        set(value) {
            runBlocking(Dispatchers.IO) {
                dataStoreService.set(DataStoreKeys.PLAYBACK_SPEED, value)
            }
        }

    override val playbackSpeedFlow: Flow<Float> = dataStoreService[DataStoreKeys.PLAYBACK_SPEED, 1.0f]
}
