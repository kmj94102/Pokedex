package com.example.pokedex.netwrok

import com.example.pokedex.netwrok.data.PokemonInfo
import retrofit2.Response
import retrofit2.http.*

interface PokemonService {

    @GET("v2/pokemon/{index}")
    suspend fun getPokemonDetail(
        @Path("index") index: Int = 1
    ) : Response<PokemonInfo>

}