package com.cap.taxi.debug

import android.util.Log

private const val APP_TAG = "aaataxi"

fun logI(message: String, tag: String = "") {
    Log.i(APP_TAG + tag, message)
}

fun logE(message: String, tag: String = "") {
    Log.e(APP_TAG + tag, message)
}

fun logE(exception: Throwable, message: String = "", tag: String = "") {
    Log.e(APP_TAG + tag, message, exception)
}