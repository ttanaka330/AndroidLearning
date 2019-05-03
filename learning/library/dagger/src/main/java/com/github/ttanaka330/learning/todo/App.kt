package com.github.ttanaka330.learning.todo

import com.github.ttanaka330.learning.todo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().application(this).build()
    }
}