package com.andrewafony.easycodeapp

import android.app.Application
import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import com.andrewafony.easycodeapp.data.local.BaseCachedDataSource
import com.andrewafony.easycodeapp.data.local.CacheDataSource
import com.andrewafony.easycodeapp.data.local.JokeCacheCallback
import com.andrewafony.easycodeapp.data.remote.*
import com.andrewafony.easycodeapp.domain.model.*
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var viewModel: ViewModel

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://v2.jokeapi.dev/")
            .build()

        viewModel = ViewModel(
            BaseModel(
                BaseCachedDataSource(Realm.getDefaultInstance()),
                BaseCloudDataSource(retrofit.create(JokeService::class.java)),
                BaseResourceManager(this)
            )
        )
    }
}

class BaseModel(
    private val cacheDataSource: CacheDataSource,
    private val cloudDataSource: CloudDataSource,
    private val resourceManager: ResourceManager
) : Model {

    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }
    private val noCachedJokes by lazy { NoCachedJokes(resourceManager) }

    private var jokeCallback: JokeCallback? = null
    private var cachedJoke: Joke? = null
    private var getJokeFromCache = false

    override fun getJoke() {
        if (getJokeFromCache) {
            cacheDataSource.getJoke(object : JokeCacheCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provideJoke(joke.toFavoriteJoke())
                }

                override fun fail() {
                    cachedJoke = null
                    jokeCallback?.provideJoke(FailedJokeUiModel(noCachedJokes.getMessage()))
                }
            })
        } else {
            cloudDataSource.getJoke(object : JokeCloudCallback {
                override fun provide(joke: Joke) {
                    cachedJoke = joke
                    jokeCallback?.provideJoke(joke.toBaseJoke())
                }

                override fun fail(errorType: ErrorType) {
                    cachedJoke = null
                    val failure =
                        if (errorType == ErrorType.SERVICE_UNAVAILABLE) noConnection else serviceUnavailable
                    jokeCallback?.provideJoke(FailedJokeUiModel(failure.getMessage()))
                }
            })
        }
    }

    override fun changeJokeStatus(jokeCallback: JokeCallback) {
        cachedJoke?.change(cacheDataSource)?.let {
            jokeCallback.provideJoke(it)
        }
    }

    override fun chooseDataSource(checked: Boolean) {
        getJokeFromCache = checked
    }

    override fun init(callback: JokeCallback) {
        this.jokeCallback = callback
    }

    override fun clear() {
        jokeCallback = null
    }
}

class TestModel(
    private val resourceManager: ResourceManager
) : Model {

    private var callback: JokeCallback? = null
    private val noConnection by lazy { NoConnection(resourceManager) }
    private val serviceUnavailable by lazy { ServiceUnavailable(resourceManager) }

    private var count = 0

    override fun getJoke() {
        Thread {
            Thread.sleep(1000)
            when (count) {
                0 -> callback?.provideJoke(BaseJokeUiModel("testText", "testPunch"))
                1 -> callback?.provideJoke(FavoriteJokeUiModel("favorite", "favorite punch"))
                2 -> callback?.provideJoke(FailedJokeUiModel(serviceUnavailable.getMessage()))
            }
            count++
            if (count == 3) count = 0
        }.start()

    }

    override fun init(callback: JokeCallback) {
        this.callback = callback
    }

    override fun clear() {
        callback = null
    }

    override fun changeJokeStatus(jokeCallback: JokeCallback) {

    }

    override fun chooseDataSource(checked: Boolean) {

    }
}