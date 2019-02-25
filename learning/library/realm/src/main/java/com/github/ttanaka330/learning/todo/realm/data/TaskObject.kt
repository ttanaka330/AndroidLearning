package com.github.ttanaka330.learning.todo.realm.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaskObject : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var completed: Boolean = false
}