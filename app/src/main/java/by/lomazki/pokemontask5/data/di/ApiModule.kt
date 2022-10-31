package by.lomazki.pokemontask5.data.di

import by.lomazki.pokemontask5.data.api.PokemonApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://pokeapi.co/api/v2/"

val apiModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create<PokemonApi>() }
}
