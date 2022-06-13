package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.domain.model.Joke

interface JokeCacheCallback {

    fun provide(joke: Joke)

    fun fail()
}