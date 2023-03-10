package com.moviestreaming.ui.home

import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.repository.FakeMovieRepository
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {



    private lateinit var movieRepository: FakeMovieRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun createViewModel() {
        val trending1 = TrendingEntity(1, "x", "y", "tv")
        val trending2 = TrendingEntity(2, "x2", "y2", "tv2")
        val trending3 = TrendingEntity(3, "x3", "y3", "tv3")
        val trending4 = TrendingEntity(4, "x4", "y4", "tv4")

        val listOfTrending = listOf(trending1, trending2, trending3, trending4)

        movieRepository = FakeMovieRepository()
        movieRepository.addTrending(listOfTrending)
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun get_TrendingFromRepository() {
        homeViewModel.trending
    }

}