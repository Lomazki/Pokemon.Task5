package by.lomazki.pokemontask5.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity

@Database(entities = [PokemonFullEntity::class, PokemonShortEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonFullDao(): PokemonFullDao

    abstract fun pokemonShortDao(): PokemonShortDao

}
