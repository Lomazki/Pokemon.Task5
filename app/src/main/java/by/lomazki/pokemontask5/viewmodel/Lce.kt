package by.lomazki.pokemontask5.viewmodel

sealed class Lce <out T>{

    object Loading: Lce<Nothing>()

    data class ContentPokemon<T>(val listPokemon: T) : Lce<T>()

    data class Error(val throwable: Throwable) : Lce<Nothing>()
}
