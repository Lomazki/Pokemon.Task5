package by.lomazki.pokemontask5.model.datasource.api

import by.lomazki.pokemontask5.constants.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiService {

    fun getPokemonApi(url: String = BASE_URL): PokemonApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create()
    }
}