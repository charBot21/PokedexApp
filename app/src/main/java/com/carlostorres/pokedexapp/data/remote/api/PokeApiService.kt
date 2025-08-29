package com.carlostorres.pokedexapp.data.remote.api

import com.carlostorres.pokedexapp.data.remote.dto.PokemonDetailDto
import com.carlostorres.pokedexapp.data.remote.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Defines the REST API endpoints for the PokeAPI.
 */
interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailDto
}