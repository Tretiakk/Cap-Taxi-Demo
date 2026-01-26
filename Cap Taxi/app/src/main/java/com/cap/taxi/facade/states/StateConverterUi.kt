package com.cap.taxi.facade.states

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.cap.taxi.domain.model.driver.DriverInfo
import com.cap.taxi.domain.model.other.OverlayMessageInfo
import com.cap.taxi.domain.model.other.OverlayNetworkInfo
import java.io.ByteArrayOutputStream


fun DriverInfoUi.toDomain(): DriverInfo {
    return DriverInfo(
        this.id,
        this.name,
        this.tariffH, this.tariffM, tariffS,
        this.rate,
        this.car,
        this.typeOfCar,
        this.experience,
        this.phoneNumber,
        this.image?.let { bitmapToJpegBytes(it.asAndroidBitmap()) },
        this.isExpended
    )
}

fun DriverInfo.toUi(): DriverInfoUi {
    return DriverInfoUi(
        this.id,
        this.name,
        this.tariffH, this.tariffM, tariffS,
        this.rate,
        this.car,
        this.typeOfCar,
        this.experience,
        this.phoneNumber,
        this.image?.let { jpegBytesToBitmap(it).asImageBitmap() },
        this.isExpended
    )
}

fun OverlayMessageInfo.toUi(): OverlayMessageInfo {
    return OverlayMessageInfo(
        this.shortDescription,
        this.description,
        this.buttonText,
        this.onButtonClick,
        this.visible
    )
}

fun OverlayNetworkInfo.toUi(): OverlayNetworkInfoUi {
    return OverlayNetworkInfoUi(
        this.isConnected
    )
}

private fun bitmapToJpegBytes(bitmap: Bitmap, quality: Int = 90): ByteArray {
    val out = ByteArrayOutputStream()
    val ok = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
    check(ok) { "Bitmap.compress failed" }
    return out.toByteArray()
}

private fun jpegBytesToBitmap(bytes: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        ?: error("decodeByteArray failed")
}