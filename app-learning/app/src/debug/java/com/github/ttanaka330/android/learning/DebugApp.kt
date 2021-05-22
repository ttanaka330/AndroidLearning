package com.github.ttanaka330.android.learning

import timber.log.Timber

class DebugApp : App() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
