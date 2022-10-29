package by.lomazki.pokemontask5.domain.repository

import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity

interface Repository {

    suspend fun getPokemonShortList(limit: Int = 40, offset: Int = 0): List<PokemonShortEntity>

    suspend fun insertPokemonShortList(pokemonList: List<PokemonShortDTO>)

    suspend fun getListPokemonFull(): List<PokemonFullEntity>

    suspend fun getPokemonFull(name: String): PokemonFullEntity

    suspend fun insertPokemonFull(pokemon: PokemonFullEntity)

    suspend fun deletePokemon(namePokemon: String)

    suspend fun clearDB()

}
