package by.lomazki.pokemontask5.viewmodel.adapter

import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemontask5.databinding.ItemPokemonBinding
import by.lomazki.pokemontask5.viewmodel.model.Pokemon

class PokemonViewHolder(
    private val binding: ItemPokemonBinding,
    val onPokemonClicked: (Pokemon) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon) {
        with(binding) {
            tvNamePokemon.text = pokemon.name
            root.setOnClickListener { onPokemonClicked(pokemon) }
        }
    }
}
