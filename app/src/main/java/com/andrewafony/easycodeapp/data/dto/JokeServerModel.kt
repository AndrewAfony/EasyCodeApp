package com.andrewafony.easycodeapp.data.dto

import com.andrewafony.easycodeapp.data.local.CacheDataSource
import com.andrewafony.easycodeapp.domain.model.BaseJoke
import com.andrewafony.easycodeapp.domain.model.FavoriteJoke
import com.andrewafony.easycodeapp.domain.model.JokeRealm
import com.google.gson.annotations.SerializedName

data class JokeServerModel(
    @SerializedName("")
    private val id: Int,
    @SerializedName("setup")
    private val setup: String,
    @SerializedName("delivery")
    private val delivery: String,
    @SerializedName("category")
    private val category: String
) {
    fun toBaseJoke() = BaseJoke(setup, delivery)

    fun toFavoriteJoke() = FavoriteJoke(setup, delivery)

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
