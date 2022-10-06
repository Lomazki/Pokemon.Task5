package by.lomazki.pokemontask5.viewmodel.model

import com.google.gson.annotations.SerializedName

data class GeneralInfo(

    @SerializedName("results")
    val pokemonList: List<Pokemon> = emptyList()

)
