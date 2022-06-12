package com.andrewafony.easycodeapp

import android.app.Application
import com.andrewafony.easycodeapp.data.dto.JokeDto
import com.andrewafony.easycodeapp.data.remote.JokeService
import com.andrewafony.easycodeapp.domain.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class App : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://v2.jokeapi.dev/")
            .build()

        viewModel = ViewModel(
//            BaseModel(
//                retrofit.create(JokeService::class.java),
//                BaseResourceManager(this)
//            )
        TestModel(BaseResourceManager(this))
        )
    }
}

class BaseModel(
    private val service: JokeService,
    private val resourceManager: ResourceManager
) : Model {

    private var callback: ResultCallback? = null
    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }

    override fun getJoke() {
        service.getJoke().enqueue(object : retrofit2.Callback<JokeDto> {
            override fun onResponse(call: Call<JokeDto>, response: Response<JokeDto>) {
                if (response.isSuccessful) {
                    callback?.provideJoke(response.body()!!.toJoke())
                }
            }

            override fun onFailure(call: Call<JokeDto>, t: Throwable) {
//                if (t is UnknownHostException) {
//                    callback?.provideError(noConnection)
//                } else {
//                    callback?.provideError(serviceUnavailable)
//                }
            }
        })
    }

    override fun init(callback: ResultCallback) {
        this.callback = callback
    }

    override fun clear() {
        callback = null
    }
}

class TestModel(
    private val resourceManager: ResourceManager
) : Model {

    private var callback: ResultCallback? = null
    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }

    private var count = 0

    override fun getJoke() {
        Thread {
            Thread.sleep(1000)
            when (count) {
                0 -> callback?.provideJoke(BaseJoke("testText", "testPunch"))
                1 -> callback?.provideJoke(FavoriteJoke("favorite", "favorite punch"))
                2 -> callback?.provideJoke(FailedJoke(serviceUnavailable.getMessage()))
            }
            count++
            if(count == 3) count = 0
        }.start()

    }

    override fun init(callback: ResultCallback) {
        this.callback = callback
    }

    override fun clear() {
        callback = null
    }
}