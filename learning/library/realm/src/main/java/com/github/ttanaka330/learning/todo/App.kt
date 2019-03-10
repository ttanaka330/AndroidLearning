package com.github.ttanaka330.learning.todo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDatabase()
    }

    private fun setupDatabase() {
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .schemaVersion(1L)
                .build()
        )
    }
}