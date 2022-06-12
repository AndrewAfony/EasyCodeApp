package com.andrewafony.easycodeapp

import androidx.annotation.DrawableRes
import com.andrewafony.easycodeapp.domain.model.Joke

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null

    fun init(callback: DataCallback) {
        this.dataCallback = callback
        model.init(object : ResultCallback {
            override fun provideJoke(joke: Joke) {
                dataCallback?.let {
                    joke.map(it)
                }
            }
        })
    }

    fun getAdvice() {
        model.getJoke()
    }

    fun clear() {
        dataCallback = null
        model.clear()
    }

    fun chooseFavorites(checked: Boolean) {

    }

}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id: Int)
}
