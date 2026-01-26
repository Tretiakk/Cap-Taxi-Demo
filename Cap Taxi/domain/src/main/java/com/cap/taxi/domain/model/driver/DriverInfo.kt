package com.cap.taxi.domain.model.driver

import com.cap.taxi.domain.model.common.DriverCarType

data class DriverInfo(
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
    val image: ByteArray?,
    val isSelected: Boolean = false,
    var isExpended: Boolean = false
)