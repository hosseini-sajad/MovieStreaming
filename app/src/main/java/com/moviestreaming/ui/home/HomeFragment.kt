package com.moviestreaming.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.moviestreaming.ui.theme.MovieStreamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    /*private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MovieStreamingTheme {
                    HomeScreenRoute()
                }
            }
        }

        /*_binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.trending.collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> binding.animProgress.visibility = View.VISIBLE
                            is UiState.Success -> {
                                binding.animProgress.visibility = View.GONE
                                val viewPagerAdapter = TrendingAdapter(
                                    uiState.data.take(5),
                                    object : ItemClickListener<BaseEntity> {
                                        override fun onItemClickListener(model: BaseEntity) {
                                            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment(model.id))
                                        }
                                    })
                                val viewPager = binding.viewpager
                                viewPager.apply {
                                    SliderPageUtil.sliderAutoChange(
                                        requireActivity(),
                                        uiState.data.size,
                                        this
                                    )
                                    adapter = viewPagerAdapter
                                }
                            }
                            is UiState.Error -> binding.animProgress.visibility = View.GONE
                        }
                    }
                }

                launch {
                    homeViewModel.topRateMovie.collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                setupImdbRecyclerView(uiState.data.take(5), binding.imdbRecyclerview)
                            }
                            is UiState.Error -> binding.animProgress.visibility = View.GONE
                        }
                    }
                }

                launch {
                    homeViewModel.popularMovies.collect { uiState ->
                        when (uiState) {
                            is UiState.Loading -> {}
                            is UiState.Success -> {
                                setupImdbRecyclerView(uiState.data.take(5), binding.newMoviesRecyclerview)
                            }

                            is UiState.Error -> binding.animProgress.visibility = View.GONE
                        }
                    }
                }

            }
        }

        return root*/
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getTrending()
        homeViewModel.getTopRateMovie()
        homeViewModel.getPopularMovies()

        binding.topImdbMoreText.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.navigation_home) {
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCategoryFragment())
            }
        }

        binding.newMoviesMoreText.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.navigation_home) {
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCategoryFragment())
            }
        }
    }*/

    /*private fun setupImdbRecyclerView(
        list: List<TopRateMovieEntity>,
        recyclerView: RecyclerView,
    ) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TopRateMovieAdapter(list, object : ItemClickListener<BaseEntity> {
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
    }*/
}