package com.moviestreaming.ui.detail

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.databinding.FragmentDetailBinding
import com.moviestreaming.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.movieDetail.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> binding.loading.visibility = View.VISIBLE
                        is UiState.Success -> {
                            binding.loading.visibility = View.INVISIBLE
                            showMovieDetail(uiState.data)
                        }
                        is UiState.Error -> {
                            binding.loading.visibility = View.INVISIBLE
                            Log.d("JJJJJJJJ", "onCreateView: ${uiState.message}")
                        }
                    }

                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.getMovieDetail(args.movieId)
    }

    private fun showMovieDetail(movieDetailEntity: MovieDetailEntity) {
        Glide.with(requireContext())
            .load(movieDetailEntity.image)
//            .error(R.drawable.ic_image_error)
            .into(binding.movieImage)

        binding.name.text = movieDetailEntity.title
        binding.genres.text = appendGenres(movieDetailEntity.genres) ?: "Nothing"
        binding.rate.text = movieDetailEntity.rate.toString()
        binding.releasedDate.text = movieDetailEntity.releasedDate
        binding.budget.text = movieDetailEntity.budget.toString()
        binding.description.text = movieDetailEntity.description
    }

    private fun appendGenres(genres: List<GenreEntity>): String? {
        var genreFormat : String? = null
        genres.forEach{
            genreFormat = it.name + ", "
        }
        return genreFormat
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}