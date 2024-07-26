package com.example.sampleapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.data.local.ProductDB
import com.example.sampleapp.data.network.ApiService
import com.example.sampleapp.data.network.ProductApi
import com.example.sampleapp.domain.entities.Post
import com.example.sampleapp.domain.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi,
    private val apiService: ApiService,
    private val productDB: ProductDB
) {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    suspend fun getProducts() {
        val products = productApi.getProducts()
        if (products.isSuccessful){
            _products.postValue(products.body())
            productDB.getProductDao().addProducts(products.body()!!)

        }
    }

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() = _posts

    fun getPost(): Flow<List<Post>> =flow{
        emit(apiService.getPost())
    }.flowOn(Dispatchers.IO)


}