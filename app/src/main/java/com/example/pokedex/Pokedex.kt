package com.example.pokedex

import android.app.Application

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