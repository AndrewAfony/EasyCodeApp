package com.andrewafony.easycodeapp.domain.model

import com.andrewafony.easycodeapp.data.local.CacheDataSource

class Joke(
    private val id: Int,
    private val setup: String,
    private val delivery: String,
    private val category: String
) {
    fun toBaseJoke() = BaseJokeUiModel(setup, delivery)

    fun toFavoriteJoke() = FavoriteJokeUiModel(setup, delivery)

    fun toJokeRealm(): JokeRealm {
        return JokeRealm().also {
            it.id = id
            it.text = setup
            it.punchLine = delivery
            it.category = category
        }
    }

    fun change(cacheDataSource: CacheDataSource) = cacheDataSource.addOrRemove(id, this)
}