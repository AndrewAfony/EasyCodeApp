package com.andrewafony.easycodeapp.data.dto

import com.andrewafony.easycodeapp.domain.model.BaseJoke
import com.google.gson.annotations.SerializedName

data class JokeDto(
    @SerializedName("")
    private val id: Int,
    @SerializedName("setup")
    private val setup: String,
    @SerializedName("delivery")
    private val delivery: String,
    @SerializedName("category")
    private val category: String
) {
    fun toJoke() = BaseJoke(setup, delivery)
}
