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
                callback.provide(response.body()!!)
            }

            override fun onFailure(call: Call<JokeServerModel>, t: Throwable) {
                if(t is UnknownHostException)
                    callback.fail(ErrorType.NO_CONNECTION)
                else
                    callback.fail(ErrorType.SERVICE_UNAVAILABLE)
            }
        })
    }
}