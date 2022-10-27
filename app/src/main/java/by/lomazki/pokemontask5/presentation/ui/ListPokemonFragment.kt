package by.lomazki.pokemontask5.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.lomazki.pokemontask5.R
import by.lomazki.pokemontask5.constants.Constants.ITEMS_TO_LOAD
import by.lomazki.pokemontask5.databinding.FragmentPokemonsListBinding
import by.lomazki.pokemontask5.extentions.addPaginationListener
import by.lomazki.pokemontask5.presentation.models.Lce
import by.lomazki.pokemontask5.presentation.ui.adapter.PokemonAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ListPokemonFragment : Fragment() {

    private var _binding: FragmentPokemonsListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        PokemonAdapter(
            context = requireContext(),
            onPokemonClicked = {
                findNavController().navigate(
                    ListPokemonFragmentDirections
                        .actionFragmentMainToFragmentDetails(it.name)
                )
            })
    }
    private val viewModel by inject<PokemonViewModel>()

    //-----------------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPokemonsListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val linearLayoutManager = LinearLayoutManager(
                view.context, LinearLayoutManager.VERTICAL, false
            )
            recyclerViewPokemon.adapter = adapter

            // Decorator
            recyclerViewPokemon.addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )

            swipeRefresh.setOnRefreshListener {
//                adapter.submitList(emptyList())       // для проверки, что приходит новый список. Но из-за него может быть ошибка с ".shareIn"
                viewModel.onRefreshedPokemons()
            }

            // Paging
            recyclerViewPokemon.addPaginationListener(linearLayoutManager, ITEMS_TO_LOAD) {
                viewModel.onLoadMore()
            }

            // Обрабатываем "поиск" в тулбаре
            toolbarMain
                .menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.onQueryChanged(newText)
                        return true
                    }
                })

            viewModel
                .currentLceFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onEach { binding.swipeRefresh.isRefreshing = false }
                .onEach { lce ->
                    when (lce) {
                        is Lce.Loading -> {
                            binding.progress.isVisible = true
                        }
                        is Lce.ContentPokemon -> {
                            adapter.submitList(lce.listPokemon.map { Lce.ContentPokemon(it) })
                            binding.progress.isVisible = false
                        }
                        is Lce.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
