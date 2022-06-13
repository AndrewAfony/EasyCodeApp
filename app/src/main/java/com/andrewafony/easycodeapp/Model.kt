package com.andrewafony.easycodeapp

import com.andrewafony.easycodeapp.domain.model.JokeUiModel

interface Model {

    fun getJoke()

    fun init(callback: JokeCallback)

    fun clear()

    fun changeJokeStatus(jokeCallback: JokeCallback)

    fun chooseDataSource(checked: Boolean)
}

interface JokeCallback {

    fun provideJoke(joke: JokeUiModel)

}