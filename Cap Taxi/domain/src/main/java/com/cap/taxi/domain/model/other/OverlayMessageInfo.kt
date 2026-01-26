package com.cap.taxi.domain.model.other


data class OverlayMessageInfo(
    val shortDescription: String = "",
    val description: String = "",
    val buttonText: String = "",
    val onButtonClick: () -> Unit = {},
    var visible: Boolean = true
    )
