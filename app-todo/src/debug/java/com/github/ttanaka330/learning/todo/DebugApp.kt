package com.github.ttanaka330.learning.todo

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho
import timber.log.Timber

class DebugApp : Application() {

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
