package com.carlostorres.pokedexapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlostorres.pokedexapp.data.mappers.toDomain
import com.carlostorres.pokedexapp.data.remote.api.PokeApiService
import com.carlostorres.pokedexapp.domain.model.Pokemon
import retrofit2.HttpException
import java.io.IOException

private const val POKEMON_STARTING_PAGE_INDEX = 0
private const val NETWORK_PAGE_SIZE = 20

/**
 * PagingSource to fetch Pokemon from the PokeAPI.
 * This class handles the logic of loading pages of data for the list view.
 * @param apiService The Retrofit service to make API calls.
 */
class PokemonPagingSource(
    private val apiService: PokeApiService
) : PagingSource<Int, Pokemon>() {

    /**
     * Loads a page of data from the API.
     * @param params Contains information about the page to load.
     * @return A LoadResult object representing the loaded page.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key ?: POKEMON_STARTING_PAGE_INDEX
        val offset = position * NETWORK_PAGE_SIZE

        return try {
            val response = apiService.getPokemonList(limit = NETWORK_PAGE_SIZE, offset = offset)
            val pokemonList = response.results.map { it.toDomain() }

            val nextKey = if (pokemonList.isEmpty()) {
                null // No more pages to load
            } else {
                position + 1
            }

            LoadResult.Page(
                data = pokemonList,
                prevKey = if (position == POKEMON_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * Provides the key for the initial refresh.
     * This is used to determine which page to load when the data is first displayed.
     */
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}