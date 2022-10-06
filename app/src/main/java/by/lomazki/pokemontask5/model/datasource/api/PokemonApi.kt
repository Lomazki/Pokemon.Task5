package by.lomazki.pokemontask5.model.datasource.api

import by.lomazki.pokemontask5.viewmodel.model.GeneralInfo
import by.lomazki.pokemontask5.viewmodel.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon?")
    suspend fun getGeneralRequest(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GeneralInfo

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon
}