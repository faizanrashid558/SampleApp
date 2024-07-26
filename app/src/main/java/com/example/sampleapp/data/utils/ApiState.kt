package com.example.sampleapp.data.utils

import com.example.sampleapp.domain.entities.Post

sealed class ApiState{
    object EmptyState:ApiState()
    class Failure(val msg:Throwable):ApiState()
    class Success(val data:List<Post>):ApiState()
    object Loading:ApiState()
}
