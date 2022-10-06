package by.lomazki.pokemontask5.viewmodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemontask5.databinding.ItemLoadingBinding
import by.lomazki.pokemontask5.databinding.ItemPokemonBinding
import by.lomazki.pokemontask5.viewmodel.Lce
import by.lomazki.pokemontask5.viewmodel.model.Pokemon

class PokemonAdapter(
    context: Context,
    private val onPokemonClicked: (Pokemon) -> Unit
) : ListAdapter<Lce<Pokemon>, RecyclerView.ViewHolder>(DIFF_UTIL) {

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

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Lce<Pokemon>>() {
            override fun areItemsTheSame(
                oldItem: Lce<Pokemon>,
                newItem: Lce<Pokemon>
            ): Boolean {
                return oldItem == newItem        // не знаю, как скастить по-человечески
//                return (oldItem as Pokemon).id == (newItem as Pokemon).id       // не работает!
            }

            override fun areContentsTheSame(
                oldItem: Lce<Pokemon>,
                newItem: Lce<Pokemon>
            ): Boolean {
                return oldItem == newItem
//                return (oldItem as Pokemon).name == (newItem as Pokemon).name &&
//                        (oldItem as Pokemon).height == (newItem as Pokemon).height &&
//                        (oldItem as Pokemon).weight == (newItem as Pokemon).weight
            }
        }
    }
}

