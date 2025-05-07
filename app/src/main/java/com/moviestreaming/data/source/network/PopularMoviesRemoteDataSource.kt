package com.moviestreaming.data.source.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moviestreaming.data.model.TopRateMovieEntity
import retrofit2.HttpException
import java.io.IOException

class PopularMoviesRemoteDataSource(
    private val apiService: ApiService
) : PagingSource<Int, TopRateMovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, TopRateMovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopRateMovieEntity> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getPopularMovies(page)

            LoadResult.Page(
                data = response.topRateMovies.map { it.toEntity() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.topRateMovies.isEmpty()) null else page.plus(1)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}