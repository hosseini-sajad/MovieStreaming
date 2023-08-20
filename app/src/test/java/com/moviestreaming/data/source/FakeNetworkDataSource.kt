package com.moviestreaming.data.source

import com.moviestreaming.data.source.network.dto.CreditsDto
import com.moviestreaming.data.source.network.dto.MovieDetailDto
import com.moviestreaming.data.source.network.dto.SimilarMoviesDto
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse
import com.moviestreaming.data.source.network.dto.TrendingResponse

internal class FakeNetworkDataSource():
    NetworkDataSource {

    private lateinit var trendingMovies: MutableList<TrendingResponse.Trending>
    private lateinit var topRateMovies: MutableList<TopRateMovieResponse.TopRateMovie>
    private lateinit var popularMovies: MutableList<TopRateMovieResponse.TopRateMovie>
    private lateinit var movieDetail: MovieDetailDto
    private lateinit var similarMovies: SimilarMoviesDto

    override suspend fun getTrending(): List<TrendingResponse.Trending>? {
        if (::trendingMovies.isInitialized) {
            trendingMovies.let {
                return it
            }
        }
        return null
    }

    override suspend fun getTopRateMovie(): List<TopRateMovieResponse.TopRateMovie>? {
        if (::topRateMovies.isInitialized) {
            topRateMovies.let {
                return it
            }
        }
        return null
    }

    override suspend fun getPopularMovies(): List<TopRateMovieResponse.TopRateMovie>? {
        if (::popularMovies.isInitialized) {
            popularMovies.let {
                return it
            }
        }
        return null
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetailDto? {
        if (::movieDetail.isInitialized) {
            movieDetail.let {
                return it
            }
        }
        return null
    }

    override suspend fun getMovieCredits(movieId: Int): CreditsDto {

        return TODO("Provide the return value")
    }

    override suspend fun getSimilarMovies(movieId: Int): SimilarMoviesDto? {
        if (::similarMovies.isInitialized) {
            similarMovies.let {
                return it
            }
        }
        return null
    }

    fun addTrending(trending: List<TrendingResponse.Trending>) {
        trendingMovies = mutableListOf()
        trendingMovies.addAll(trending)
    }

    fun addTopReteMovies(topRate: List<TopRateMovieResponse.TopRateMovie>) {
        topRateMovies = mutableListOf()
        topRateMovies.addAll(topRate)
    }

    fun addPopularMovies(popularMoviesList: List<TopRateMovieResponse.TopRateMovie>) {
        popularMovies = mutableListOf()
        popularMovies.addAll(popularMoviesList)
    }

    fun addMovieDetail(movieDetailDto: MovieDetailDto) {
        movieDetail = movieDetailDto
    }

    fun addSimilarMovie(similarMoviesDto: SimilarMoviesDto) {
        similarMovies = similarMoviesDto
    }

}