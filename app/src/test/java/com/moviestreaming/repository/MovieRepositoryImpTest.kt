package com.moviestreaming.repository

import com.moviestreaming.data.source.FakeNetworkDataSource
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.network.dto.TrendingResponse
import com.moviestreaming.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class MovieRepositoryImpTest() {
    private val movie1 = TrendingResponse.Trending(false,
        "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg",
        "",
        listOf(1, 2, 3),
        1,
        "tv",
        "Avengers: Infinity War",
        listOf("x", "y"),
        "en",
        "Avengers: Infinity War",
        "Avengers: Infinity War",
        "q",
        12.9,
        "/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg",
        "1990",
        "Avengers",
        false,
        9.9,
        10
    )
    private val movie2 = TrendingResponse.Trending(false,
        "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg",
        "",
        listOf(1, 2, 3),
        2,
        "tv",
        "wwwwwww: Infinity War",
        listOf("x", "y"),
        "en",
        "wwwww: Infinity War",
        "wwwwwwww: Infinity War",
        "q",
        12.9,
        "/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg",
        "1990",
        "wwwwwwww",
        false,
        9.9,
        10
    )

    private val topRateMovie = TopRateMovieResponse.TopRateMovie(
        false,"/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", listOf(1, 2),
        1, "en", "Wally", "ww", 11.8,
        "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", "2000", "Wally",
        false, 8.8, 8
    )

    private val topRateMovie2 = TopRateMovieResponse.TopRateMovie(
        false,"/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", listOf(2, 3),
        1, "en", "Wally2", "ww2", 10.5,
        "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", "2001", "Wall2y",
        false, 5.8, 7
    )

    private val remoteTrending = listOf( movie1, movie2)
    private val remoteTopRateMovies = listOf(topRateMovie, topRateMovie2)

    private lateinit var networkDataSource: FakeNetworkDataSource
    private lateinit var movieRepositoryImp: MovieRepositoryImp

    /*@Before
    fun createRepository() {
        networkDataSource = FakeNetworkDataSource(remoteTrending)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
    }*/

    @Test
    fun getTrending_RequestAllTrendingFromNetworkDatasource() {
        networkDataSource = FakeNetworkDataSource()
        networkDataSource.addTrending(remoteTrending)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val trending = movieRepositoryImp.getTrending()

            assertEquals(Result.Success(remoteTrending.map { it.toEntity() }.toList()), trending.toList().first())
        }
    }

    @Test
    fun getTopRateMovie_From_Network_Datasource() {
        networkDataSource = FakeNetworkDataSource()
        networkDataSource.addTopReteMovies(remoteTopRateMovies)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val topRateMovies = movieRepositoryImp.getTopRateMovie()

            assertEquals(Result.Success(remoteTopRateMovies.map { it.toEntity() }.toList()), topRateMovies.toList().first())
        }
    }

    @Test
    fun `null trending, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val trending = movieRepositoryImp.getTrending().first()
            assertTrue(trending is Result.Error)
        }
    }

}