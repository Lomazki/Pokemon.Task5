package by.lomazki.pokemontask5.data.api

import by.lomazki.pokemontask5.data.model.GeneralResultDTO
import by.lomazki.pokemontask5.data.model.PokemonFullDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon?")
    suspend fun getGeneralRequest(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GeneralResultDTO

    @GET("pokemon/{name}")
    suspend fun getPokemonDTO(@Path("name") name: String): PokemonFullDTO
}
