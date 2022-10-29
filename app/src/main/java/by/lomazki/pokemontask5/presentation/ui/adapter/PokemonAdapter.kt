package by.lomazki.pokemontask5.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemontask5.data.model.pokemonshort.PokemonShortEntity
import by.lomazki.pokemontask5.databinding.ItemLoadingBinding
import by.lomazki.pokemontask5.databinding.ItemPokemonBinding
import by.lomazki.pokemontask5.presentation.models.Lce

class PokemonAdapter(
    context: Context,
    private val onPokemonClicked: (PokemonShortEntity) -> Unit
) : ListAdapter<Lce<PokemonShortEntity>, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Lce.ContentPokemon -> TYPE_POKEMON
            Lce.Loading -> TYPE_LOADING
            is Lce.Error -> TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_POKEMON -> {
                PokemonViewHolder(
                    binding = ItemPokemonBinding.inflate(layoutInflater, parent, false),
                    onPokemonClicked = onPokemonClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Unsupported ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val pokemon = getItem(position)) {
            is Lce.ContentPokemon -> {
                checkNotNull(holder as PokemonViewHolder) { "Incorrect ViewHolder $pokemon" }
                holder.bind(pokemon.listPokemon)
            }
            Lce.Loading -> {}
            is Lce.Error -> {}
        }
    }

    companion object {

        private const val TYPE_POKEMON = 0
        private const val TYPE_LOADING = 1
        private const val TYPE_ERROR = 2

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Lce<PokemonShortEntity>>() {
            override fun areItemsTheSame(
                oldItem: Lce<PokemonShortEntity>,
                newItem: Lce<PokemonShortEntity>
            ): Boolean {
                return (oldItem as? PokemonShortEntity)?.name == (newItem as? PokemonShortEntity)?.name
            }

            override fun areContentsTheSame(
                oldItem: Lce<PokemonShortEntity>,
                newItem: Lce<PokemonShortEntity>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
