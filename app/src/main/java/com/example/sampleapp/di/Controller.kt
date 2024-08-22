package com.example.sampleapp.di

import android.app.Application
import com.example.sampleapp.data.local.AppPreferences
import com.example.sampleapp.domain.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Controller : Application() {
    override fun onCreate() {
        super.onCreate()



    }
}