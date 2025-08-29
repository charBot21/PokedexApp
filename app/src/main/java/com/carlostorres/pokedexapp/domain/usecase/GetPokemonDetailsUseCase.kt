package com.carlostorres.pokedexapp.domain.usecase

import com.carlostorres.pokedexapp.domain.model.PokemonDetails
import com.carlostorres.pokedexapp.domain.repository.PokemonRepository
import com.carlostorres.pokedexapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting the details of a specific Pokemon.
 * This class encapsulates the business logic for fetching Pokemon details.
 */
class GetPokemonDetailsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<Resource<PokemonDetails>> {
        return repository.getPokemonDetails(name)
    }
}