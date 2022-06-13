package com.andrewafony.easycodeapp.domain.model

import androidx.annotation.DrawableRes
import com.andrewafony.easycodeapp.DataCallback
import com.andrewafony.easycodeapp.R

class BaseJokeUiModel(text: String?, punchline: String?): JokeUiModel(text, punchline) {
    override fun getIconResId(): Int = R.drawable.ic_favorite_border
}

class FavoriteJokeUiModel(text: String?, punchline: String?): JokeUiModel(text, punchline) {
    override fun getIconResId(): Int = R.drawable.ic_favorite
}

class FailedJokeUiModel(text: String?): JokeUiModel(text, "") {
    override fun getIconResId(): Int = 0
}

abstract class JokeUiModel(
    private val text: String?,
    private val punchline: String?
) {
    private fun text() = "$text\n$punchline"

    @DrawableRes
    abstract fun getIconResId(): Int

    fun map(callback: DataCallback) = callback.run {
        provideText(text())
        provideIconRes(getIconResId())
    }
}
