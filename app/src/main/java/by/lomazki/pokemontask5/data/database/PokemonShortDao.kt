package by.lomazki.pokemontask5.data.database

import androidx.room.*
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity

@Dao
interface PokemonShortDao {

    @Query("select * from PokemonShortEntity")
    suspend fun getPokemonsShortEntity(): List<PokemonShortEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonsShort: List<PokemonShortEntity>)

    @Query("DELETE FROM PokemonShortEntity")
    suspend fun clear()

    //    @Query("DELETE FROM PokemonShortEntity where name = :name")
    @Delete
    suspend fun deletePokemonShort(pokemon: PokemonShortEntity)
}
