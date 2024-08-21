package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.data.local.AppPreferences
import com.example.sampleapp.data.local.ProductDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppPrefModule {
    @Singleton
    @Provides
    fun provideAppPref(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }
}