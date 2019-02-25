package com.github.ttanaka330.learning.todo.realm

import android.app.Application
import android.os.StrictMode
import com.facebook.stetho.Stetho

class DebugApp : Application() {

    override fun onCreate() {
        setupStetho()
        setupStrict()
        super.onCreate()
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