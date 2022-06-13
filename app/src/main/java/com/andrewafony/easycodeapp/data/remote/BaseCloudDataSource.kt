package com.andrewafony.easycodeapp.data.remote

import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import retrofit2.Call
import retrofit2.Response
import java.net.UnknownHostException

class BaseCloudDataSource(private val service: JokeService) : CloudDataSource {

    override fun getJoke(callback: JokeCloudCallback) {
        service.getJoke().enqueue(object : retrofit2.Callback<JokeServerModel> {
            override fun onResponse(
                call: Call<JokeServerModel>,
                response: Response<JokeServerModel>
            ) {
                callback.provide(response.body()!!.toJoke())
            }

            override fun onFailure(call: Call<JokeServerModel>, t: Throwable) {
                val errorType = if (t is UnknownHostException)
                    ErrorType.NO_CONNECTION
                else
                    ErrorType.SERVICE_UNAVAILABLE
                callback.fail(errorType)
            }
        })
    }
}