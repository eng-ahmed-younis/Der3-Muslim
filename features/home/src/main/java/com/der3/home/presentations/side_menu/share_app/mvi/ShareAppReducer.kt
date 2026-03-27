package com.der3.home.presentations.side_menu.share_app.mvi

import com.der3.mvi.Reducer

class ShareAppReducer : Reducer<ShareAppAction, ShareAppState> {
    override fun reduce(action: ShareAppAction, state: ShareAppState): ShareAppState {
        return when (action) {
            is ShareAppAction.ShowLoading -> state.copy(isLoading = true, error = null)
            is ShareAppAction.ShowError -> state.copy(isLoading = false, error = action.message)
            is ShareAppAction.GetDownloadLink -> state.copy(downloadLink = action.link)
            is ShareAppAction.DismissError -> state.copy(error = null)
        }
    }
}
