package com.andrewafony.easycodeapp.data.local

import com.andrewafony.easycodeapp.data.dto.JokeServerModel
import com.andrewafony.easycodeapp.domain.model.Joke
import com.andrewafony.easycodeapp.domain.model.JokeRealm
import io.realm.Realm

class BaseCachedDataSource(private val realm: Realm): CacheDataSource {

    override fun getJoke(jokeCacheCallback: JokeCacheCallback) {
        realm.let {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if(jokes.isEmpty())
                jokeCacheCallback.fail()
            else {
                jokes.random().let { joke ->
                    jokeCacheCallback.provide(
                        JokeServerModel(
                            joke.id,
                            joke.text,
                            joke.punchLine,
                            joke.category
                        )
                    )
                }
            }
        }
    }

    override fun addOrRemove(id: Int, joke: JokeServerModel): Joke {
        realm.let {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id", id).findFirst()
            return if (jokeRealm == null){
                val newJoke = joke.toJokeRealm()
                it.executeTransactionAsync { transaction ->
                    transaction.insert(newJoke)
                }
                joke.toFavoriteJoke()
            } else {
                it.executeTransactionAsync {
                    jokeRealm.deleteFromRealm()
                }
                joke.toBaseJoke()
            }
        }
    }
}