package com.der3.mvi

interface Reducer<A : MviAction, S : MviState> {

    fun reduce(action: A, state: S): S
}
