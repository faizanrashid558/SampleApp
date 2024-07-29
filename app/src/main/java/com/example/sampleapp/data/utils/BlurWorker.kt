package com.example.sampleapp.data.utils

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BlurWorker (context:Context,params:WorkerParameters):Worker(context,params) {
   override fun doWork(): Result {
        val resourceUri = inputData.getString("Uri")
        return try {
            if (resourceUri.isNullOrEmpty()) {
//                logger.logd("Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            val outputData = blurAndWriteImageToFile(resourceUri)
            Thread.sleep(4000)
            Result.success(outputData)
        } catch (throwable: Throwable) {
//            logger.loge(throwable.message.toString())
            Result.failure()
        }
    }

    private fun blurAndWriteImageToFile(resourceUri: String): Data {
        return Data.Builder().putString("Result",resourceUri).build()
    }
}