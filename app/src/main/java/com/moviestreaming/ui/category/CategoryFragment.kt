package com.moviestreaming.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moviestreaming.R
import com.moviestreaming.data.model.base.BaseEntity
import com.moviestreaming.databinding.FragmentCategoryBinding
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.ui.home.HomeFragmentDirections
import com.moviestreaming.ui.home.HomeViewModel
import com.moviestreaming.ui.home.adapter.MovieAdapter
import com.moviestreaming.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.topRateMovie.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.animProgress.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            binding.animProgress.visibility = View.GONE
                            setupImdbRecyclerView(binding.categoryRecyclerView)
                        }

                        is UiState.Error -> {
                            binding.animProgress.visibility = View.GONE
                            Log.d("JJJJJJJJ", "onCreateView: ${uiState.message}")
                        }
                    }
                }
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                homeViewModel.popularMovies.collect { uiState ->
//                    when (uiState) {
//                        is UiState.Loading -> {}
//                        is UiState.Success -> {
//                            setupImdbRecyclerView(uiState, binding.categoryRecyclerView)
//                        }
//
//                        is UiState.Error -> {
//                            binding.animProgress.visibility = View.GONE
//                            Log.d("JJJJJJJJ", "onCreateView: ${uiState.message}")
//                        }
//                    }
//                }
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setupImdbRecyclerView(
        recyclerView: RecyclerView,
    ) {
        recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = MovieAdapter(object : ItemClickListener<BaseEntity> {
                override fun onItemClickListener(model: BaseEntity) {
                    if (findNavController().currentDestination?.id == R.id.navigation_home) {
                        findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment(model.id))
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