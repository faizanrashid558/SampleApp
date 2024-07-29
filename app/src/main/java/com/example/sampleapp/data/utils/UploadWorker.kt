package com.example.sampleapp.data.utils

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UploadWorker(context:Context,params:WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {
        val count = inputData.getInt("COUNT",100)
        for(i : Int in 0 until count){
            CoroutineScope(Dispatchers.IO).launch {
                Log.i("WORKERTAG","Uploading $i")
                delay(100)
            }
        }
        val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.US)
        val currentDate = time.format(Date())
        val outPutDate = Data.Builder().putString("DATE",currentDate).build()
        return Result.success(outPutDate)
    }
}