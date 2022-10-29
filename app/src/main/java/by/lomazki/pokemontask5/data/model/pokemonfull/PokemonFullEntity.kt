package by.lomazki.pokemontask5.data.model.pokemonfull

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@JvmInline
//value class PokemonId(val rawId: Int)

@Entity
data class PokemonFullEntity(
//    val id: PokemonId,
    val id: Int,
    @PrimaryKey
    val name: String,
    val height: Int,
    val weight: Int,
    val url: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    var favorite: Boolean
)
