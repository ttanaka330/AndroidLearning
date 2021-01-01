package com.github.ttanaka330.learning.todo

import android.os.StrictMode
import com.facebook.stetho.Stetho
import timber.log.Timber

class DebugApp : App() {

    override fun onCreate() {
        setupStrict()
        super.onCreate()
        setupStetho()
        setupLogger()
    }

    private fun setupLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setupStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun setupStrict() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }
}
