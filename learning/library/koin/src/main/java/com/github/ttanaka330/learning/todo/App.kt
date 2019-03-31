package com.github.ttanaka330.learning.todo

import android.app.Application
import com.github.ttanaka330.learning.todo.di.appModule
import org.koin.android.ext.android.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}