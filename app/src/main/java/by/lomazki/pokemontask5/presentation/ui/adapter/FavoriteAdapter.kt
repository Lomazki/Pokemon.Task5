package by.lomazki.pokemontask5.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import by.lomazki.pokemontask5.data.model.PokemonFullEntity
import by.lomazki.pokemontask5.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private val context: Context,
    private val onClickedCheckBox: (PokemonFullEntity) -> Unit
) :
    ListAdapter<PokemonFullEntity, FavoriteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onPokemonClicked = onClickedCheckBox
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PokemonFullEntity>() {
            override fun areItemsTheSame(
                oldItem: PokemonFullEntity,
                newItem: PokemonFullEntity
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PokemonFullEntity,
                newItem: PokemonFullEntity
            ): Boolean {
                return oldItem.height == newItem.height &&
                        oldItem.weight == newItem.weight
            }
        }
    }
}
