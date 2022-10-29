package by.lomazki.pokemontask5.presentation.di

import by.lomazki.pokemontask5.presentation.ui.details.DetailsViewModel
import by.lomazki.pokemontask5.presentation.ui.favourite.FavoriteViewModel
import by.lomazki.pokemontask5.presentation.ui.map.MapViewModel
import by.lomazki.pokemontask5.presentation.ui.listpokemon.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::PokemonViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::MapViewModel)
}
