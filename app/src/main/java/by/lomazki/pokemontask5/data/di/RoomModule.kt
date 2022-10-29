package by.lomazki.pokemontask5.data.di

import androidx.room.Room
import by.lomazki.pokemontask5.data.database.PokemonDatabase
import org.koin.dsl.module

val roomModule = module {
    single {
        Room
            .databaseBuilder(
                get(),
                PokemonDatabase::class.java,
                "database"
            ).allowMainThreadQueries().build()
    }

    single { get<PokemonDatabase>().pokemonFullDao() }

    single { get<PokemonDatabase>().pokemonShortDao() }
}
