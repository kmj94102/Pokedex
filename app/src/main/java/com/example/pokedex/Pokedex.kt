package com.example.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Pokedex : Application() {
    
    companion object {
        private lateinit var application : Pokedex
        fun getInstance() : Pokedex = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}