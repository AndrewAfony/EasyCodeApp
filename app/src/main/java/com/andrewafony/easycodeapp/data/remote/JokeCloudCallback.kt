package com.andrewafony.easycodeapp.data.remote

import com.andrewafony.easycodeapp.domain.model.Joke

interface JokeCloudCallback {

    fun provide(joke: Joke)
    fun fail(errorType: ErrorType)
}