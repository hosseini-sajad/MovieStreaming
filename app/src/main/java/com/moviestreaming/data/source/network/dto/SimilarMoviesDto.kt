package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.utils.Constants
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class SimilarMoviesDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val similarMoviesDto: List<SimilarMovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) {
    @Keep
    data class SimilarMovieDto(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("poster_path")
        val posterPath: String,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Double,
        @SerializedName("vote_count")
        val voteCount: Int
    ) : DomainMapper<TopRateMovieEntity> {
        override fun toEntity() = TopRateMovieEntity(
            id, title, posterPath, Constants.MOVIE,
            if(genreIds.isNotEmpty()) genreIds[0] else -1 , voteAverage
        )
    }
}