package com.moviestreaming.repository

import com.moviestreaming.data.source.FakeNetworkDataSource
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto.GenreDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto.ProductionCompanyDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto.ProductionCountryDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
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

    private val remoteMovieDetail = MovieDetailDto(
        false,"/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", listOf(1,2), 1000,
        genresDto = listOf(GenreDto(1, "action"), GenreDto(2, "action")),
        "homepages", 1001, "25556", "en", "Brave", "This movie is about",
        12.8, "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", productionCompaniesDto = listOf(
            ProductionCompanyDto(1, "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", "Sam", "US"),
            ProductionCompanyDto(1, "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", "Sam", "US")
        ), productionCountriesDto = listOf(ProductionCountryDto("dfsa12", "US"),
            ProductionCountryDto("dfsa12", "US")),
        "2019", 11, 56, listOf(),"true", "ssaa", "Brave", false, 8.5, 7
    )

    private val remoteSimilarMovies = SimilarMoviesDto(
        1,
        listOf(
            SimilarMoviesDto.SimilarMovieDto(
                false, "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", listOf(1, 2, 3),
                1003, "en", "Last Of Us", "This part is", 12.5,
                "/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg", "2022", "Last Of Us", false, 8.5, 6
            )
        ),
        1,
        1
    )

    private val remoteTrending = listOf( movie1, movie2)
    private val remoteTopRateMovies = listOf(topRateMovie, topRateMovie2)
    private val remotePopularMovies = listOf(topRateMovie, topRateMovie2)

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
    fun getPopularMovie_From_Network_Datasource() {
        networkDataSource = FakeNetworkDataSource()
        networkDataSource.addPopularMovies(remoteTopRateMovies)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val topRateMovies = movieRepositoryImp.getPopularMovies()

            assertEquals(Result.Success(remotePopularMovies.map { it.toEntity() }.toList()), topRateMovies.toList().first())
        }
    }

    @Test
    fun getMovieDetail_From_Network_Datasource() {
        networkDataSource = FakeNetworkDataSource()
        networkDataSource.addMovieDetail(remoteMovieDetail)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val movieDetail = movieRepositoryImp.getMovieDetails(1001)

            assertEquals(Result.Success(remoteMovieDetail.toEntity()), movieDetail.toList().first())
        }
    }

    @Test
    fun getSimilarMovies_From_Network_Datasource() {
        networkDataSource = FakeNetworkDataSource()
        networkDataSource.addSimilarMovie(remoteSimilarMovies)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val movieDetail = movieRepositoryImp.getSimilarMovies(1003)

            assertEquals(Result.Success(remoteSimilarMovies.similarMoviesDto.map { it.toEntity() }), movieDetail.toList().first())
        }
    }

    @Test
    fun `null trending movie, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val trending = movieRepositoryImp.getTrending().first()
            assertTrue(trending is Result.Error)
        }
    }

    @Test
    fun `null top rate movie, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val topRateMovies = movieRepositoryImp.getTopRateMovie().first()
            assertTrue(topRateMovies is Result.Error)
        }
    }

    @Test
    fun `null popular movie, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val popularMovies = movieRepositoryImp.getPopularMovies().first()
            assertTrue(popularMovies is Result.Error)
        }
    }

    @Test
    fun `null movie detail, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val movieDetail = movieRepositoryImp.getMovieDetails(1001).first()
            assertTrue(movieDetail is Result.Error)
        }
    }

    @Test
    fun `null similar movies, return error`() {
        networkDataSource = FakeNetworkDataSource()
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
        runBlocking {
            val movieDetail = movieRepositoryImp.getSimilarMovies(1003).first()
            assertTrue(movieDetail is Result.Error)
        }
    }

}