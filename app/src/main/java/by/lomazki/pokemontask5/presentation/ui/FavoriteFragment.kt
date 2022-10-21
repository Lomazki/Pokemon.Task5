package by.lomazki.pokemontask5.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.lomazki.pokemontask5.databinding.FragmentFavoriteBinding
import by.lomazki.pokemontask5.presentation.ui.adapter.FavoriteAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by inject<FavoriteViewModel>()

    private val adapter by lazy {
        FavoriteAdapter(requireContext(),
            onClickedCheckBox = {
                findNavController().navigate(
                    FavoriteFragmentDirections.actionFragmentFavouritesToFragmentPokemon(it.name)
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerViewFavorite.adapter = adapter

            // Decorator
            recyclerViewFavorite.addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )

            viewModel.getListFlow()
                .onEach {
                    Log.d("favPokeList.value", " в submitList   ${viewModel.favPokeList.value}, ")
                    adapter.submitList(it)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
