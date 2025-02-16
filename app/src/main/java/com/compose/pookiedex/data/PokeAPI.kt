package com.compose.pookiedex.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {
    @GET("pokemon/{nameOrId}")
    suspend fun getPokemon(@Path("nameOrId") nameOrId: String): PokemonResponse
}