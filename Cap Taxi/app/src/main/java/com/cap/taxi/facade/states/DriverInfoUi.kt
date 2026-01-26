package com.cap.taxi.facade.states

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import com.cap.taxi.domain.model.common.DriverCarType

@Immutable
data class DriverInfoUi(
    val id: Int,
    val name: String,
    val tariffH: Int,
    val tariffM: Int,
    val tariffS: Int,
    val rate: Float,
    val car: String,
    val typeOfCar: DriverCarType,
    val experience: Int,
    val phoneNumber: String,
    val image: ImageBitmap?,
    val isExpended: Boolean = false
)