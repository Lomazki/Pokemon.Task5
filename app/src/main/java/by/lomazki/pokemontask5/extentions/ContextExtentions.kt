package by.lomazki.pokemontask5.extentions

import android.content.Context
import by.lomazki.pokemontask5.model.datasource.room.DatabaseApplication
import by.lomazki.pokemontask5.model.datasource.room.PokemonDatabase

val Context.appDatabase: PokemonDatabase
    get() = when (this) {
        is DatabaseApplication -> database
        else -> applicationContext.appDatabase
    }