package com.andrewafony.easycodeapp.domain.model

import com.andrewafony.easycodeapp.R

interface JokeFailure {
    fun getMessage(): String
}

class NoConnection(private val resource: ResourceManager) : JokeFailure {

    override fun getMessage(): String = resource.getString(R.string.no_connection)
}

class ServiceUnavailable(private val resource: ResourceManager) : JokeFailure {

    override fun getMessage(): String = resource.getString(R.string.service_unavaliable)
}