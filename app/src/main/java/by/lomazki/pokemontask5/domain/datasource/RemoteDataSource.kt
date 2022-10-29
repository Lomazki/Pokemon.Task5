package by.lomazki.pokemontask5.domain.datasource

import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullDTO
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortDTO

interface RemoteDataSource {

    suspend fun getPokemonListApi(limit: Int, offset: Int): Result<List<PokemonShortDTO>>

    suspend fun getPokemonApi(name: String): Result<PokemonFullDTO>
}
