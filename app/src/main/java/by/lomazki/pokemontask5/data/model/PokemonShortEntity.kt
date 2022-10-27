package by.lomazki.pokemontask5.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Pokemon для БД сокращенный
@Entity
data class PokemonShortEntity(
    @PrimaryKey
    val name: String,
    val url: String
)
