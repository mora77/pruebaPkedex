package com.example.pruebapokedex.data.api

import com.example.pruebapokedex.data.dto.PokemonDetailDto
import com.example.pruebapokedex.data.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonApi {
    @GET("pokemon")
    suspend fun listPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto


    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(@Path("idOrName") idOrName: String): PokemonDetailDto
}