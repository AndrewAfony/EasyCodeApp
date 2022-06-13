package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.domain.model.Joke
import com.andrewafony.easycodeapp.domain.model.JokeUiModel

class TestCacheDataSource: CacheDataSource {

    private val list = ArrayList<Pair<Int, Joke>>()

    override fun addOrRemove(id: Int, joke: Joke): JokeUiModel {
        val found = list.find { it.first == id }
        return if(found != null){
            val joke = found.second.toBaseJoke()
            list.removeAt(id)
            joke
        } else {
            list.add(Pair(id, joke))
            joke.toFavoriteJoke()
        }
    }

    override fun getJoke(jokeCacheCallback: JokeCacheCallback) {
        if(list.isEmpty())
            jokeCacheCallback.fail()
        else
            jokeCacheCallback.provide(list.random().second)
    }
}