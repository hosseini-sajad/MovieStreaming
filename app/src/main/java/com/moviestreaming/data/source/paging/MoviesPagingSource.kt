package com.moviestreaming.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moviestreaming.data.source.network.ApiService
import com.moviestreaming.data.source.network.dto.TopRateMovieResponse.TopRateMovie
import retrofit2.HttpException
import javax.inject.Inject

private const val TMDB_STARTING_PAGE_INDEX: Int = 1

class MoviesPagingSource @Inject constructor(
    private val apiService: ApiService,
) : PagingSource<Int, TopRateMovie>() {
    override fun getRefreshKey(state: PagingState<Int, TopRateMovie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopRateMovie> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getTopRateMovie(currentPage)
            val data = response.topRateMovies
            val responseData = mutableListOf<TopRateMovie>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else-1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }

}