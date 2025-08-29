package com.carlostorres.pokedexapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.carlostorres.pokedexapp.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the Pokemon List screen.
 * It uses Hilt for dependency injection to get the use case.
 * @param getPokemonListUseCase The use case to fetch the paginated list of Pokemon.
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    /**
     * A Flow of PagingData that represents the stream of Pokemon list data.
     * `cachedIn(viewModelScope)` caches the data in the ViewModel, so it survives
     * configuration changes like screen rotations.
     */
    val pokemonPagingFlow = getPokemonListUseCase()
        .cachedIn(viewModelScope)
}