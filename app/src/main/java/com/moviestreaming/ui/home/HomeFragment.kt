package com.moviestreaming.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.databinding.FragmentHomeBinding
import com.moviestreaming.ui.ItemClickListener
import com.moviestreaming.ui.home.adapter.SliderAdapter
import com.moviestreaming.utils.UiState
import com.moviestreaming.utils.mapper.SliderPageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemClickListener<TrendingEntity> {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.trending.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> binding.animProgress.visibility = View.VISIBLE
                        is UiState.Success -> {
                            binding.animProgress.visibility = View.GONE
                            val viewPagerAdapter = SliderAdapter(uiState.data, this@HomeFragment)
                            val viewPager = binding.viewpager
                            viewPager.apply {
                                SliderPageUtil.sliderAutoChange(requireActivity(), uiState.data.size, this)
                                adapter = viewPagerAdapter
                            }

//                            Methods.viewPageTransformer(binding.viewpager)
//                            setSliderIndicator(list.size)
                        }
                        is UiState.Error -> {
                            binding.animProgress.visibility = View.GONE
                            Log.d("JJJJJJJJ", "onCreateView: ${uiState.message}")
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getTrending()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickListener(model: TrendingEntity) {
        Log.d("YYYYYYYYY", "Yesssssss")
    }
}