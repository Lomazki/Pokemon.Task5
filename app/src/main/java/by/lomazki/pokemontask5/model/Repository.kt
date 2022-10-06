package by.lomazki.pokemontask5.model

import by.lomazki.pokemontask5.model.datasource.api.ApiService
import by.lomazki.pokemontask5.model.datasource.room.PokemonDatabase
import by.lomazki.pokemontask5.viewmodel.model.Pokemon


class Repository(
    private val database: PokemonDatabase,
    private val apiService: ApiService
) {

    suspend fun getListPokemonApi(limit: Int, offset: Int = 0): List<Pokemon> {
        var result = emptyList<Pokemon>()
        runCatching {
            apiService
                .getPokemonApi()
                .getGeneralRequest(limit, offset)
                .pokemonList
        }.onSuccess {
            result = it
        }.onFailure {
            result = emptyList()    // TODO вывести Error
        }
        return result
    }

    suspend fun getPokemonApi(name: String): Pokemon {
        var result = Pokemon(0, "", 0, 0)
        runCatching {
            apiService.getPokemonApi().getPokemon(name)
        }.onSuccess {
            result = it
        }.onFailure {
            // TODO() доделать Error
        }
        return result
    }

    fun replaceRoom(pokemons: List<Pokemon>) {
        database.pokemonDao().insertAll(
            pokemons
                .map {pokemon ->
                    pokemon.toPokemonEntity(pokemon)
                }
                .toList()
        )
    }

    fun getListPokemonRoom(): List<Pokemon> {
        return database.pokemonDao().getAll().map {
            it.toPokemon(it)
        }
    }
}
