package com.carlostorres.pokedexapp.domain.repository

import androidx.paging.PagingData
import com.carlostorres.pokedexapp.domain.model.Pokemon
import com.carlostorres.pokedexapp.domain.model.PokemonDetails
import com.carlostorres.pokedexapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Pokemon repository.
 * This defines the contract for data operations related to Pokemon.
 * The implementation will be in the data layer.
 */
interface PokemonRepository {

    /**
     * Gets a paginated list of Pokemon.
     * @return A Flow of PagingData containing Pokemon objects.
     */
    fun getPokemonList(): Flow<PagingData<Pokemon>>

    /**
     * Gets the details for a specific Pokemon by its name.
     * @param name The name of the Pokemon.
     * @return A Flow emitting Resource states (Loading, Success, Error) for the Pokemon details.
     */
    fun getPokemonDetails(name: String): Flow<Resource<PokemonDetails>>
}