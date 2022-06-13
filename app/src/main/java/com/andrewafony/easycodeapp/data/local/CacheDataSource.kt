package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.domain.model.Joke
import com.andrewafony.easycodeapp.domain.model.JokeUiModel

interface CacheDataSource {

    fun addOrRemove(id: Int, joke: Joke): JokeUiModel

    fun getJoke(jokeCacheCallback: JokeCacheCallback)
}