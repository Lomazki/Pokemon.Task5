package by.lomazki.pokemontask5.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.lomazki.pokemontask5.databinding.FragmentDetailsBinding
import coil.load
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by inject<DetailsViewModel> {
        parametersOf(args.name)
    }

    //--------------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarDetail.setupWithNavController(findNavController())

        flow<Nothing> { viewModel.loadPokemon() }.launchIn(lifecycleScope)

        viewModel
            .currentPokemonFullFlow
            .onEach {
                with(binding) {
                    ivPokemon.load(it.avatarUrl)
                    tvNamePokemon.text = args.name
                    tvHeight.text = it.height.toString()
                    tvWeight.text = it.weight.toString()
                    tvIsFavourite.text = viewModel.checkIsFavorite().toString()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.buttonAdd.setOnClickListener {
            viewModel.currentPokemonFullFlow
                .onEach {
                    viewModel.addToFavourites()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        binding.buttonRemove.setOnClickListener {
            viewModel.currentPokemonFullFlow
                .onEach {
                    viewModel.removeFromFavourites()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
