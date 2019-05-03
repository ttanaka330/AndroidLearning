package com.github.ttanaka330.learning.todo

import android.os.StrictMode
import com.facebook.stetho.Stetho

class DebugApp : App() {

    override fun onCreate() {
        setupStrict()
        super.onCreate()
        setupStetho()
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