package com.musdev.lingonote

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        //NoteDatabase.build(app)


    }

    override fun onTerminate() {
        super.onTerminate()
    }
}