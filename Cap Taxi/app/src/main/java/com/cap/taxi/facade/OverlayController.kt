package com.cap.taxi.facade

import com.cap.taxi.domain.usecase.other.IsNetworkConnectionUc
import com.cap.taxi.facade.states.OverlayMessageInfoUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OverlayController (
    private val isNetworkConnectionUc: IsNetworkConnectionUc
) {

    private val _isNetworkConnected = MutableStateFlow(false)
    private val _messageState = MutableStateFlow(OverlayMessageInfoUi(visible = false))

    val isNetworkConnected = _isNetworkConnected.asStateFlow()
    val messageState = _messageState.asStateFlow()

    // Network connection check
    // If network is not connected shows error screen
    fun isNetworkConnected(): Boolean {
        val isConnected = isNetworkConnectionUc.execute()

        _isNetworkConnected.value = isConnected

        return isConnected
    }

    fun message(
        shortDescription: String,
        description: String,
        buttonText: String,
        onButtonClick: (hideMessage: () -> Unit) -> Unit = { it() /* hideMessage() */ }
    ) {

        _messageState.value = OverlayMessageInfoUi(
            shortDescription = shortDescription,
            description = description,
            buttonText = buttonText,
            onButtonClick = {
                onButtonClick({
                    _messageState.value = _messageState.value.copy(visible = false)
                })
            }
        )
    }
}