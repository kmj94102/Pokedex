package com.example.pokedex.netwrok

import com.example.pokedex.netwrok.data.PokemonInfo
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val service: PokemonService
) {

    suspend fun getPokemonInfo(
        index: Int,
        onSuccessListener : (PokemonInfo) -> Unit,
        onFailureListener : () -> Unit
    ) =
        try {
            val result = service.getPokemonDetail(index = index)

            if (result.isSuccessful) {
                result.body()?.let(onSuccessListener)?: onFailureListener()
            } else {
                onFailureListener()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onFailureListener()
        }
}