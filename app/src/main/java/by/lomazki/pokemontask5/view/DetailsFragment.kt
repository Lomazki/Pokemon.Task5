package by.lomazki.pokemontask5.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import by.lomazki.pokemontask5.databinding.FragmentDetailsBinding
import by.lomazki.pokemontask5.model.ServiceLocator
import by.lomazki.pokemontask5.viewmodel.DetailsViewModel
import coil.load
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<DetailsFragmentArgs>()

    private val viewModel by viewModels<DetailsViewModel> {
        viewModelFactory {
            initializer {
                DetailsViewModel(ServiceLocator.provideDataSource(), args.name)
            }
        }
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

        flow<Nothing> { viewModel.loadPokemon() }.launchIn(lifecycleScope)

        viewModel
            .currentPokemonFlow
            .onEach {
                with(binding) {
                    ivPokemon.load(it.avatarUrl)
                    tvNamePokemon.text = args.name
                    tvHeight.text = it.height.toString()
                    tvWeight.text = it.weight.toString()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
