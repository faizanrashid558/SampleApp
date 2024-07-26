package com.example.sampleapp.data.network

import com.example.sampleapp.domain.entities.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ApiService @Inject constructor(private val client: HttpClient) {
    suspend fun getPost(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }
}