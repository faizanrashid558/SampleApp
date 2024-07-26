package com.example.sampleapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class ShareVM : ViewModel() {
    val message = MutableLiveData<Int>()


    init {
        message.value=0
    }
    // function to send message
    fun updateVal() {
        message.value = message.value?.plus(1)
    }
}