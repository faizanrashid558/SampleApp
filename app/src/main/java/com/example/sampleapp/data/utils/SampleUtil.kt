package com.example.sampleapp.data.utils

import javax.inject.Inject

class SampleUtil @Inject constructor(private val logger: Logger){
    init {
        logMsg()
        logMsg2()
        logMsg3()
    }

    private fun logMsg() {
        logger.logd("Sample Util Test message")
    }

    private fun logMsg2() {
        logger.logd("Sample Util Test message")
    }

    private fun logMsg3() {
        logger.logd("Sample Util Test message")
    }
}