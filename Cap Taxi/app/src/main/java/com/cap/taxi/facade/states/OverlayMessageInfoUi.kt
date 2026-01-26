package com.cap.taxi.facade.states

import androidx.compose.runtime.Immutable


@Immutable
data class OverlayMessageInfoUi(
    val shortDescription: String = "",
    val description: String = "",
    val buttonText: String = "",
    val onButtonClick: () -> Unit = {},
    var visible: Boolean = true
    )
