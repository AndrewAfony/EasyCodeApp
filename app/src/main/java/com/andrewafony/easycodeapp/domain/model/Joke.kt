package com.andrewafony.easycodeapp.domain.model

import androidx.annotation.DrawableRes
import com.andrewafony.easycodeapp.DataCallback
import com.andrewafony.easycodeapp.R

class BaseJoke(text: String?, punchline: String?): Joke(text, punchline) {
    override fun getIconResId(): Int = R.drawable.ic_favorite_border
}

class FavoriteJoke(text: String?, punchline: String?): Joke(text, punchline) {
    override fun getIconResId(): Int = R.drawable.ic_favorite
}

class FailedJoke(text: String?): Joke(text, "") {
    override fun getIconResId(): Int = 0
}

abstract class Joke(
    private val text: String?,
    private val punchline: String?
) {
    protected fun getJokeUi() = "$text\n$punchline"

    @DrawableRes
    abstract fun getIconResId(): Int

    fun map(callback: DataCallback) = callback.run {
        provideText(getJokeUi())
        provideIconRes(getIconResId())
    }
}
