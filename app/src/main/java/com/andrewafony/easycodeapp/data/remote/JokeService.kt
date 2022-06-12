package com.andrewafony.easycodeapp.data.remote

import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import retrofit2.Call
import retrofit2.http.GET

interface JokeService {

    @GET("/joke/Any")
    fun getJoke() : Call<JokeServerModel>
}

enum class ErrorType{
    NO_CONNECTION,
    SERVICE_UNAVAILABLE
}