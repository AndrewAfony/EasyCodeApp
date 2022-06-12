package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import com.andrewafony.easycodeapp.domain.model.Joke

interface CacheDataSource {

    fun addOrRemove(id: Int, joke: JokeServerModel): Joke

    fun getJoke(jokeCacheCallback: JokeCacheCallback)
}