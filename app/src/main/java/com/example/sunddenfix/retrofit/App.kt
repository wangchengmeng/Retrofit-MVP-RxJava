package com.example.sunddenfix.retrofit

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        var INSTANCE: App? = null

        fun getInstance() = INSTANCE
    }
}