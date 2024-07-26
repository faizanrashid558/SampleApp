package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.data.constants.Constants
import com.example.sampleapp.data.local.ProductDB
import com.example.sampleapp.data.network.ApiService
import com.example.sampleapp.data.network.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    // Engine -> through which request is made : 1. CIO (Coroutine based I/O) 2. Android 3. OkHttp
    // Serializers -> Convert response from json to kotlin object : 1. Gson 2. Jackson 3. Kotlinx.Serialization
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                    disableHtmlEscaping()
                    serializeNulls()
                }
            }
            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService {
        return ApiService(httpClient)
    }
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
}