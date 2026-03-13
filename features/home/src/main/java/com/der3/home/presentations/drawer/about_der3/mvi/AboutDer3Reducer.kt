package com.der3.home.presentations.drawer.about_der3.mvi

import com.der3.mvi.Reducer

class AboutDer3Reducer : Reducer<AboutDer3Action, AboutDer3State> {
    override fun reduce(action: AboutDer3Action, state: AboutDer3State): AboutDer3State {
        return when (action) {
            is AboutDer3Action.UpdateVersion -> state.copy(version = action.version)
            is AboutDer3Action.UpdateLoading -> state.copy(isLoading = action.isLoading)
            is AboutDer3Action.UpdateError -> state.copy(error = action.error)
        }
    }
}
