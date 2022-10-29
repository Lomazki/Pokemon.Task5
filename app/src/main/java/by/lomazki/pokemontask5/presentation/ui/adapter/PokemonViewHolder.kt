package by.lomazki.pokemontask5.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.databinding.ItemPokemonBinding

class PokemonViewHolder(
    private val binding: ItemPokemonBinding,
    val onPokemonClicked: (PokemonShortEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemonShort: PokemonShortEntity) {
        with(binding) {
            tvNamePokemon.text = pokemonShort.name
            root.setOnClickListener { onPokemonClicked(pokemonShort) }
        }
    }
}
