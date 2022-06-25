package com.example.pokedex.util

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/"

    fun getDetailImage(index: Int) : String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${index}.svg"

    fun getDotImage(index: Int) : String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png"

}