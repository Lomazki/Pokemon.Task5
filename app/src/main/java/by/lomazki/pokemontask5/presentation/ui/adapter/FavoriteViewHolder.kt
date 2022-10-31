package by.lomazki.pokemontask5.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemontask5.data.model.pokemonfull.PokemonFullEntity
import by.lomazki.pokemontask5.databinding.ItemFavoriteBinding
import coil.load

class FavoriteViewHolder(
    private val binding: ItemFavoriteBinding,
    private val onPokemonClicked: (PokemonFullEntity) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: PokemonFullEntity) {
        with(binding) {
            avatarFavorite.load(pokemon.avatarUrl)
            nameFavorite.text = pokemon.name
            weightFavorite.text = String.format(WEIGHT, pokemon.weight.toString())
            heightFavorite.text = String.format(HEIGHT, pokemon.height.toString())
            root.setOnClickListener { onPokemonClicked(pokemon) }
        }
    }
}

private const val WEIGHT = "Weight:  %s"
private const val HEIGHT = "Height:  %s"