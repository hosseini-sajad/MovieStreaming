package com.moviestreaming.ui.home

import com.moviestreaming.MainCoroutineRule
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

    @Before
    fun createViewModel() {
        val trending1 = TrendingEntity(1, "x", "y", "tv")
        val trending2 = TrendingEntity(2, "x2", "y2", "tv2")
        val trending3 = TrendingEntity(3, "x3", "y3", "tv3")
        val trending4 = TrendingEntity(4, "x4", "y4", "tv4")

        listOfTrending = listOf(trending1, trending2, trending3, trending4)

        movieRepository = FakeMovieRepository()
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertTrue(homeViewModel.trending.value is UiState.Loading)
    }

    @Test
    fun getAllTrendingFromServer() = runTest {
        movieRepository.addTrending(listOfTrending)
        homeViewModel.getTrending()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.trending.collect() }

        assertTrue(homeViewModel.trending.value is UiState.Success)
        val item = (homeViewModel.trending.value as UiState.Success<List<TrendingEntity>>).data
        assertEquals(listOfTrending, item)

        collectJob.cancel()

    }

    @Test
    fun `empty data from server, return error`() = runTest {
        homeViewModel.getTrending()
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { homeViewModel.trending.collect()}
        val trending = homeViewModel.trending.value
        assertTrue(trending is UiState.Error)
        val message = trending.message
        assertEquals("Server problem", message)
        collectJob.cancel()
    }

}