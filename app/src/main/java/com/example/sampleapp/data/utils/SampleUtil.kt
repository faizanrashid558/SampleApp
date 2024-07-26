package com.example.sampleapp.data.utils

import javax.inject.Inject

class SampleUtil @Inject constructor(private val logger: Logger){
    init {
        logMsg()
    }

    private fun logMsg() {
        logger.logd("Sample Util Test message")
    }
}