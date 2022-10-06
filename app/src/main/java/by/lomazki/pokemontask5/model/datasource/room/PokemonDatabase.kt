package by.lomazki.pokemontask5.model.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import by.lomazki.pokemontask5.viewmodel.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): Dao

}