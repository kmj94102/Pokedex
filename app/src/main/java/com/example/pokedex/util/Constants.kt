package com.example.pokedex.util

import android.content.Context
import android.widget.Toast

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/"

    fun getDetailImage(index: Int) : String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index}.png"

    fun getDotImage(index: Int) : String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png"

    fun Context.makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}