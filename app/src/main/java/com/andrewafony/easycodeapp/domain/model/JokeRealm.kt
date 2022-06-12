package com.andrewafony.easycodeapp.domain.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class JokeRealm: RealmObject() {
    @PrimaryKey var id: Int = -1
    var text: String = ""
    var punchLine: String = ""
    var category: String = ""
}