package com.andrewafony.easycodeapp.data.remote

import com.andrewafony.easycodeapp.data.dto.JokeServerModel

interface JokeCloudCallback {

    fun provide(joke: JokeServerModel)
    fun fail(errorType: ErrorType)
}