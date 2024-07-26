package com.example.sampleapp.data.network

import com.example.sampleapp.domain.entities.Product
import retrofit2.Response
import retrofit2.http.GET


interface ProductApi {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}
