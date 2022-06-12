package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.data.dto.JokeServerModel

interface JokeCacheCallback {

    fun provide(jokeServerModel: JokeServerModel)

    fun fail()
}