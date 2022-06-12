package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import com.andrewafony.easycodeapp.domain.model.Joke

class TestCacheDataSource: CacheDataSource {

    private val list = ArrayList<Pair<Int, JokeServerModel>>()

    override fun addOrRemove(id: Int, jokeServerModel: JokeServerModel): Joke {
        val found = list.find { it.first == id }
        return if(found != null){
            val joke = found.second.toBaseJoke()
            list.removeAt(id)
            joke
        } else {
            list.add(Pair(id, jokeServerModel))
            jokeServerModel.toFavoriteJoke()
        }
    }

    override fun getJoke(jokeCacheCallback: JokeCacheCallback) {
        if(list.isEmpty())
            jokeCacheCallback.fail()
        else
            jokeCacheCallback.provide(list.random().second)
    }
}