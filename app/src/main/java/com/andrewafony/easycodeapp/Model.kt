package com.andrewafony.easycodeapp

import com.andrewafony.easycodeapp.domain.model.Joke
import com.andrewafony.easycodeapp.domain.model.JokeFailure

interface Model {

    fun getJoke()

    fun init(callback: ResultCallback)

    fun clear()
}

interface ResultCallback {

    fun provideJoke(joke: Joke)

}