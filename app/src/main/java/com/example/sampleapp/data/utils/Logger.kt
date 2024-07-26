package com.example.sampleapp.data.utils

import android.util.Log
import javax.inject.Inject

class Logger @Inject constructor() {
    private val TAG = "logger"

    fun logd(message: String) {
        Log.d(TAG, message)
    }
    fun loge(message: String) {
        Log.e(TAG, message)
    }
}