package com.moviestreaming.ui.home

import com.moviestreaming.MainCoroutineRule
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.repository.FakeMovieRepository
import com.moviestreaming.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var movieRepository: FakeMovieRepository
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listOfTrending: List<TrendingEntity>
    private lateinit var listOfTopRateMovies: List<TopRateMovieEntity>
    private lateinit var listOfPopularMovies: List<TopRateMovieEntity>

    @Before
    fun createViewModel() {
        val trending1 = TrendingEntity(1, "x", "y", "tv")
        val trending2 = TrendingEntity(2, "x2", "y2", "tv2")
        val trending3 = TrendingEntity(3, "x3", "y3", "tv3")
        val trending4 = TrendingEntity(4, "x4", "y4", "tv4")

        listOfTrending = listOf(trending1, trending2, trending3, trending4)

        val topRateMovie1 = TopRateMovieEntity(1, "a", "b", 1, "movie", 8.5)
        val topRateMovie2 = TopRateMovieEntity(2, "a2", "b2", 2, "movie", 8.8)
        val topRateMovie3 = TopRateMovieEntity(3, "a3", "b3", 3, "movie", 8.9)

        listOfTopRateMovies = listOf(topRateMovie1, topRateMovie2, topRateMovie3)

        listOfPopularMovies = listOf(topRateMovie1, topRateMovie2, topRateMovie3)

        movieRepository = FakeMovieRepository()
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertTrue(homeViewModel.trending.value is UiState.Loading)
    }

    @Test
    fun `trending from server, return success`() = runTest {
        movieRepository.addTrending(listOfTrending)
        homeViewModel.getTrending()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.trending.collect() }

        assertTrue(homeViewModel.trending.value is UiState.Success)
        val item = (homeViewModel.trending.value as UiState.Success<List<TrendingEntity>>).data
        assertEquals(listOfTrending, item)

        collectJob.cancel()

    }

    @Test
    fun `top rate movies from server, return success`() = runTest {
        movieRepository.addTopRateMovies(listOfTopRateMovies)
        homeViewModel.getTopRateMovie()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.topRateMovie.collect() }

        assertTrue(homeViewModel.topRateMovie.value is UiState.Success)
        val item = (homeViewModel.topRateMovie.value as UiState.Success<List<TopRateMovieEntity>>).data
        assertEquals(listOfTopRateMovies, item)

        collectJob.cancel()

    }

    @Test
    fun `popular movies from server, return success`() = runTest {
        movieRepository.addPopularMovies(listOfPopularMovies)
        homeViewModel.getPopularMovies()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.popularMovies.collect() }

        assertTrue(homeViewModel.popularMovies.value is UiState.Success)
        val item = (homeViewModel.popularMovies.value as UiState.Success<List<TopRateMovieEntity>>).data
        assertEquals(listOfPopularMovies, item)

        collectJob.cancel()

    }

    @Test
    fun `empty trending from server, return error`() = runTest {
        homeViewModel.getTrending()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.trending.collect()}
        val trending = homeViewModel.trending.value
        assertTrue(trending is UiState.Error)
        collectJob.cancel()
    }

    @Test
    fun `empty top rate movies from server, return error`() = runTest {
        homeViewModel.getTopRateMovie()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.topRateMovie.collect()}
        val trending = homeViewModel.topRateMovie.value
        assertTrue(trending is UiState.Error)
        collectJob.cancel()
    }

    @Test
    fun `empty popular movies from server, return error`() = runTest {
        homeViewModel.getPopularMovies()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.popularMovies.collect()}
        val trending = homeViewModel.popularMovies.value
        assertTrue(trending is UiState.Error)
        collectJob.cancel()
    }

}