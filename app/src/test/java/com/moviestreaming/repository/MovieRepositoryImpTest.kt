package com.moviestreaming.repository

import com.moviestreaming.data.source.FakeNetworkDataSource
import com.moviestreaming.data.source.network.dto.TrendingResponse
import com.moviestreaming.data.model.TrendingEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
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
        1,
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

    val movie3 = TrendingEntity(
        1,
        "got",
        "PPPP",
        "tv"
    )

    val movie4 = TrendingEntity(
        2,
        "got2",
        "PPPP2",
        "tv2"
    )

    private val remoteTrending = flowOf(listOf( movie1, movie2))
    private val remoteTrending2 = flowOf(movie3, movie4, movie4)

    private lateinit var networkDataSource: FakeNetworkDataSource
    private lateinit var movieRepositoryImp: MovieRepositoryImp

    @Before
    fun createRepository() {
        networkDataSource = FakeNetworkDataSource(remoteTrending)
        movieRepositoryImp = MovieRepositoryImp(networkDataSource)
    }

    @Test
    fun getTrending_RequestAllTrendingFromNetworkDatasource() {
        runBlocking {
            val trending = movieRepositoryImp.getTrending()

            assertEquals(remoteTrending.map { it.map { it.toEntity() } }.toList(), trending.toList())
        }
    }

}