package com.der3.data_store.impl

import com.der3.data_store.api.DataStoreRepository
import kotlinx.coroutines.Dispatchers
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

}
