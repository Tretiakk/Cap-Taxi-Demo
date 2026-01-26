package com.cap.taxi.domain.usecase.other

import com.cap.taxi.domain.data.INetworkState

class IsNetworkConnectionUc(private val network: INetworkState) {

    fun execute(): Boolean {
        return network.isNetworkConnected()
    }
}