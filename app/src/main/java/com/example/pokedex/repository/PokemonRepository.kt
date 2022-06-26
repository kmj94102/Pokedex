package com.example.pokedex.repository

import com.example.pokedex.netwrok.PokemonClient
import com.example.pokedex.netwrok.data.PokemonInfoResult
import com.example.pokedex.netwrok.data.mapper
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val client: PokemonClient
) {

    suspend fun getPokemonInfo(
        index : Int,
        onSuccessListener: (PokemonInfoResult) -> Unit,
        onFailureListener: () -> Unit
    ) {
        client.getPokemonInfo(
            index = index,
            onSuccessListener = {
                onSuccessListener(it.mapper())
            },
            onFailureListener = onFailureListener
        )
    }

}