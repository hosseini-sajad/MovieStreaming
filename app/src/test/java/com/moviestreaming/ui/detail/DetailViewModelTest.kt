package com.moviestreaming.ui.detail

import com.moviestreaming.MainCoroutineRule
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.repository.FakeMovieRepository
import com.moviestreaming.utils.Constants
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
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private lateinit var movieRepository: FakeMovieRepository
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var movieDetail: MovieDetailEntity
    private lateinit var listOfSimilarMovies: List<TopRateMovieEntity>

    @Before
    fun createViewModel() {
        val topRateMovie1 = TopRateMovieEntity(1, "a", "b", Constants.MOVIE, 1, 8.5)
        val topRateMovie2 = TopRateMovieEntity(2, "a2", "b2", Constants.MOVIE, 2, 8.8)
        val topRateMovie3 = TopRateMovieEntity(3, "a3", "b3", Constants.MOVIE, 3, 8.9)

        movieDetail = MovieDetailEntity(
            1,
            "Last Of Us",
            "a3",
            Constants.MOVIE,
            9.5,
            "2022",
            1000,
            "This part is about",
            listOf(GenreEntity(1, "Action"), GenreEntity(1, "Action"))
        )

        listOfSimilarMovies = listOf(topRateMovie1, topRateMovie2, topRateMovie3)

        movieRepository = FakeMovieRepository()
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun `detail movie from server, return success`() = runTest {
        movieRepository.addMovieDetail(movieDetail)
        detailViewModel.getMovieDetail(1003)
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { detailViewModel.movieDetail.collect() }

        assertTrue(detailViewModel.movieDetail.value is UiState.Success)
        val item = (detailViewModel.movieDetail.value as UiState.Success<MovieDetailEntity>).data
        assertEquals(movieDetail, item)

        collectJob.cancel()
    }

    @Test
    fun `similar movies from server, return success`() = runTest {
        movieRepository.addSimilarMovies(listOfSimilarMovies)
        detailViewModel.getSimilarMovies(1003)
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { detailViewModel.similarMovies.collect() }

        assertTrue(detailViewModel.similarMovies.value is UiState.Success)
        val item = (detailViewModel.similarMovies.value as UiState.Success<List<TopRateMovieEntity>>).data
        assertEquals(listOfSimilarMovies, item)

        collectJob.cancel()
    }

    @Test
    fun `empty similar movies from server, return error`() = runTest {
        detailViewModel.getSimilarMovies(1003)
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { detailViewModel.similarMovies.collect()}
        val trending = detailViewModel.similarMovies.value
        assertTrue(trending is UiState.Error)
        collectJob.cancel()
    }

    @Test
    fun `empty movie detail from server, return error`() = runTest {
        detailViewModel.getMovieDetail(1003)
        val collectJob = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { detailViewModel.movieDetail.collect()}
        val trending = detailViewModel.movieDetail.value
        assertTrue(trending is UiState.Error)
        collectJob.cancel()
    }

}