package com.andrewafony.easycodeapp.data.remote

interface CloudDataSource {

    fun getJoke(callback: JokeCloudCallback)
}

