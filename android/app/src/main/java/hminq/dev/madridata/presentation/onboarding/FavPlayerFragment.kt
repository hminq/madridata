package hminq.dev.madridata.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hminq.dev.madridata.databinding.FragmentFavPlayerBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavPlayerFragment : Fragment() {

    private var _binding: FragmentFavPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavPlayerViewModel by viewModels()
    private lateinit var adapter: FavPlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
    }

    private fun setupRecyclerView() {
        adapter = FavPlayerAdapter { player ->
            viewModel.onPlayerClicked(player)
        }

        val spanCount = 2
        val middleSpacing = (12 * resources.displayMetrics.density).toInt()
        val bottomSpacing = (16 * resources.displayMetrics.density).toInt()

        binding.rvFavPlayer.apply {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = this@FavPlayerFragment.adapter
            addItemDecoration(
                FavPlayerItemDecoration(
                    spanCount = spanCount,
                    middleSpacing = middleSpacing,
                    bottomSpacing = bottomSpacing
                )
            )
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FavPlayerUiState.Loading -> {
                            adapter.setLoading(true)
                        }
                        is FavPlayerUiState.Success -> {
                            adapter.submitPlayers(state.players)
                        }
                        is FavPlayerUiState.Error -> {
                            // Could show error message here
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
