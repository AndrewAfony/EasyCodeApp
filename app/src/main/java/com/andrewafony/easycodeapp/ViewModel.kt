package com.andrewafony.easycodeapp

import androidx.annotation.DrawableRes
import com.andrewafony.easycodeapp.domain.model.JokeUiModel

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null

    private val jokeCallback = object : JokeCallback {
        override fun provideJoke(joke: JokeUiModel) {
            dataCallback?.let {
                joke.map(it)
            }
        }
    }

    fun init(callback: DataCallback) {
        this.dataCallback = callback
        model.init(jokeCallback)
    }

    fun getAdvice() {
        model.getJoke()
    }

    fun clear() {
        dataCallback = null
        model.clear()
    }

    fun chooseFavorites(checked: Boolean) {
        model.chooseDataSource(checked)
    }

    fun changeJokeStatus() {
        model.changeJokeStatus(jokeCallback)
    }

}

interface DataCallback {

    fun provideText(text: String)

    fun provideIconRes(@DrawableRes id: Int)
}
