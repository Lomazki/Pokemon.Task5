package by.lomazki.pokemontask5.viewmodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    var id: Int,
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
    var url: String = "",
    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String = ""
) {

    fun toPokemon(pokemonEntity: PokemonEntity): Pokemon {
        return Pokemon(
            pokemonEntity.id,
            pokemonEntity.name,
            pokemonEntity.height,
            pokemonEntity.weight,
            pokemonEntity.url
        )
    }
}

/* Database требует создавать все поля сразу, то нужно создать для нее
отдельный data class PokemonEntity c соответствующими полями.
В обоих классах необходимо создать функции для перевода из Pokemon
в PokemonEntity и наоборот.
*/

