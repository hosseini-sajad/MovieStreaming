package com.moviestreaming.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moviestreaming.R
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.base.BaseEntity
import com.moviestreaming.databinding.FragmentCategoryBinding
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.ui.home.HomeViewModel
import com.moviestreaming.ui.home.adapter.TopRateMovieAdapter
import com.moviestreaming.utils.CategoryType
import com.moviestreaming.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val args: CategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                when(args.categoryType) {
                    CategoryType.TOP_RATE -> {
                        homeViewModel.topRateMovie.collect { uiState ->
                            when (uiState) {
                                is UiState.Loading -> {
                                    binding.animProgress.visibility = View.VISIBLE
                                }
                                is UiState.Success -> {
                                    binding.animProgress.visibility = View.GONE
                                    setupImdbRecyclerView(uiState, binding.categoryRecyclerView)
                                }
                                is UiState.Error -> binding.animProgress.visibility = View.GONE
                            }
                        }
                    }
                    CategoryType.NEW_MOVIE -> {
                        homeViewModel.popularMovies.collect { uiState ->
                            when (uiState) {
                                is UiState.Loading -> {}
                                is UiState.Success -> {
                                    setupImdbRecyclerView(uiState, binding.categoryRecyclerView)
                                }
                                is UiState.Error -> binding.animProgress.visibility = View.GONE
                            }
                        }
                    }
                }

            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarLayout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupImdbRecyclerView(
        uiState: UiState.Success<List<TopRateMovieEntity>>,
        recyclerView: RecyclerView,
    ) {
        recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = TopRateMovieAdapter(uiState.data, object : ItemClickListener<BaseEntity> {
                override fun onItemClickListener(model: BaseEntity) {
                    if (findNavController().currentDestination?.id == R.id.categoryFragment) {
                        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToDetailFragment(model.id))
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}