package com.andrewafony.easycodeapp.data.dto

import com.andrewafony.easycodeapp.data.local.CacheDataSource
import com.andrewafony.easycodeapp.domain.model.BaseJokeUiModel
import com.andrewafony.easycodeapp.domain.model.FavoriteJokeUiModel
import com.andrewafony.easycodeapp.domain.model.Joke
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
   fun toJoke() = Joke(id, setup, delivery, category)
}
