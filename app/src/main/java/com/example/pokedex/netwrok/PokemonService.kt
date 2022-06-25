package com.example.pokedex.netwrok

import retrofit2.http.*

interface PokemonService {

    @GET("v2/pokemon/{index}")
    fun getPokemonDetail(
        @Path("index") index: Int = 1
    )

}