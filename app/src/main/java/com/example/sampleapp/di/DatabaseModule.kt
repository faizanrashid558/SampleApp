package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.data.local.ProductDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideProductDb(@ApplicationContext context: Context): ProductDB {
        return Room.databaseBuilder(context, ProductDB::class.java, "ProductDB").build()
    }
}