package com.andrewafony.easycodeapp.data.remote

import com.andrewafony.easycodeapp.data.dto.JokeDto
import retrofit2.Call
import retrofit2.http.GET

interface JokeService {

    @GET("/joke/Any")
    fun getJoke() : Call<JokeDto>
}

enum class ErrorType{
    NO_CONNECTION,
    OTHER
}