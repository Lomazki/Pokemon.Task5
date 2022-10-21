package by.lomazki.pokemontask5.domain.datasource

import by.lomazki.pokemontask5.data.model.PokemonFullDTO
import by.lomazki.pokemontask5.data.model.PokemonShortDTO

interface RemoteDataSource {

    suspend fun getPokemonListApi(limit: Int, offset: Int): Result<List<PokemonShortDTO>>

    suspend fun getPokemonApi(name: String): Result<PokemonFullDTO>
}
