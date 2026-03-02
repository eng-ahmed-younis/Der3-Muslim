package com.der3.home.presentations.zekr_details

import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsAction
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsIntent
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsReducer
import com.der3.home.presentations.zekr_details.mvi.ZekrDetailsState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.mvi.MviEffect.*
import com.der3.screens.Screens
import com.der3.screens.Screens.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ZekrDetailsViewModel @Inject constructor() : MviBaseViewModel<ZekrDetailsState,ZekrDetailsAction, ZekrDetailsIntent>(
    initialState = ZekrDetailsState(),
    reducer = ZekrDetailsReducer()
) {
    override fun handleIntent(intent: ZekrDetailsIntent) {
        when(intent){
            is ZekrDetailsIntent.Back ->{
                onEffect(effect = Navigate(Back()))
            }

            is ZekrDetailsIntent.LoadZekrDetails -> {}
        }
    }


}