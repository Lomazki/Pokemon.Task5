package by.lomazki.pokemontask5.data.database

import androidx.room.*
import by.lomazki.pokemontask5.data.model.PokemonFullEntity

@Dao
interface PokemonFullDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonFullEntity: PokemonFullEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonFullListEntity: List<PokemonFullEntity>)

    @Query("SELECT * FROM PokemonFullEntity WHERE name = :name")
    suspend fun getByName(name: String): PokemonFullEntity

    @Query("SELECT * FROM PokemonFullEntity")
    suspend fun getPokemonFullEntityList(): List<PokemonFullEntity>

    // Удаляем конкретного покемона по имени
//    @Query("DELETE FROM PokemonFullEntity where name = :name")
    @Delete
    suspend fun deletePokemonFull(pokemon: PokemonFullEntity)

    @Query("DELETE FROM PokemonFullEntity")
    suspend fun clear()
}
