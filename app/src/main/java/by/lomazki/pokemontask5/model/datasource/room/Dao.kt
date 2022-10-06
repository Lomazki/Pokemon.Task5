package by.lomazki.pokemontask5.model.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.lomazki.pokemontask5.viewmodel.model.PokemonEntity

@Dao
interface Dao {

    @Query("SELECT * FROM pokemonEntity")
    fun getAll(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<PokemonEntity>)
}
