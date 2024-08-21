package com.example.sampleapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.utils.ApiState
import com.example.sampleapp.domain.entities.Product
import com.example.sampleapp.domain.repository.ProductRepository
import com.example.sampleapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    val productLiveData: LiveData<List<Product>>
        get() = productRepository.products

    private val _apiStateFlow : MutableStateFlow<ApiState> =  MutableStateFlow(ApiState.EmptyState)
    val apiStateFlow:StateFlow<ApiState> = _apiStateFlow

    fun getPost()  =viewModelScope.launch {
        productRepository.getPost()
            .onStart {
                _apiStateFlow.value =ApiState.Loading
            }.catch {e->
                _apiStateFlow.value =ApiState.Failure(e)
            }.collect{response->
                _apiStateFlow.value =ApiState.Success(response)
            }
    }
    init {
//        viewModelScope.launch {
//            delay(5000)
//            productRepository.getProducts()
//        }
    }

}