package com.github.ttanaka330.learning.todo

import android.app.Application
import com.github.ttanaka330.learning.todo.di.dataModule
import com.github.ttanaka330.learning.todo.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, uiModule)
        }
    }
}
