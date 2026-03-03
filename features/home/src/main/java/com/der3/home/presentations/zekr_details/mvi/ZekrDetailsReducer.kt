package com.der3.home.presentations.zekr_details.mvi

import android.util.Log
import com.der3.mvi.Reducer
import javax.inject.Inject

class ZekrDetailsReducer @Inject constructor() : Reducer<ZekrDetailsAction, ZekrDetailsState> {
    override fun reduce(
        action: ZekrDetailsAction,
        state: ZekrDetailsState
    ): ZekrDetailsState {
        return when (action) {
            is ZekrDetailsAction.OnLoading -> state.copy(isLoading = action.isLoading)

            is ZekrDetailsAction.OnZekrDetailsLoaded -> {
                state.copy(
                    zekrDetails = action.zekrDetails
                )
            }

            is ZekrDetailsAction.UpdateZekrReadingCount -> {
                val total = state.zekrDetails.repeatCount.coerceAtLeast(1)

                val newCount = (state.currentCount + 1)
                    .coerceAtMost(total)
                state.copy(currentCount = newCount)
            }


            is ZekrDetailsAction.UpdateAudioState -> {
                Log.i("ZekrDetailsReducer", "isplaying ${action.state.isPlaying}")
                state.copy(
                    audioState = action.state,
                    error = action.state.error // optional: keep error here too
                )
            }

            is ZekrDetailsAction.OnError -> state.copy(error = action.message)

            is ZekrDetailsAction.ExpandDropdownMenu -> {
                state.copy(isMenuExpanded = !state.isMenuExpanded)
            }

            is ZekrDetailsAction.UpdateDeaultFontSize -> {
                state.copy(zekrFontSize = action.font)
            }

            ZekrDetailsAction.FontSizeSheetVisibility -> {
                state.copy(fontSizeSheetVisibility = !state.fontSizeSheetVisibility)
            }
        }
    }
}