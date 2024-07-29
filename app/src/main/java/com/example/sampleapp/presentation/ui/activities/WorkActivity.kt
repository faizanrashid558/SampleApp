package com.example.sampleapp.presentation.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.sampleapp.R
import com.example.sampleapp.data.utils.BlurWorker
import com.example.sampleapp.data.utils.UploadWorker
import com.example.sampleapp.databinding.ActivityWorkBinding
import dagger.hilt.android.qualifiers.ApplicationContext

class WorkActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initClicks()

    }

    private fun initViews() {
        binding.apply {

        }
    }

    private fun initClicks() {
        binding.apply {
            startBtn.setOnClickListener {
                startWorker()
            }
            blurBtn.setOnClickListener {
                startBlurWorker()
            }
        }
    }

    private fun startBlurWorker() {
        val data = Data.Builder().putString("Uri","Image Blurred").build()
        val uploadRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java)
            .setInputData(data)
            .build()
        val worker = WorkManager.getInstance(applicationContext)
        worker.enqueue(uploadRequest)
        worker.getWorkInfoByIdLiveData(uploadRequest.id).observe(this@WorkActivity, Observer {
            binding.progresstxt.text = it.state.name
            if(it.state.isFinished){
                Toast.makeText(this@WorkActivity, "Date: ${it.outputData.getString("Result")}", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun startWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true).build()
        val data = Data.Builder().putInt("COUNT",120).build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        val worker = WorkManager.getInstance(applicationContext)
        worker.enqueue(uploadRequest)
        worker.getWorkInfoByIdLiveData(uploadRequest.id).observe(this@WorkActivity, Observer {
            binding.progresstxt.text = it.state.name
            if(it.state.isFinished){
                Toast.makeText(this@WorkActivity, "Date: ${it.outputData.getString("DATE")}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}