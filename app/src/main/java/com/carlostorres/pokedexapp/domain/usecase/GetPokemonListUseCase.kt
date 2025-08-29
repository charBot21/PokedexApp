package com.carlostorres.pokedexapp.domain.usecase

import androidx.paging.PagingData
import com.carlostorres.pokedexapp.domain.model.Pokemon
import com.carlostorres.pokedexapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting the paginated list of Pokemon.
 * This class acts as an intermediary between the ViewModel and the Repository.
 * The 'operator fun invoke' allows this class to be called as if it were a function.
 */
class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return repository.getPokemonList()
    }
}