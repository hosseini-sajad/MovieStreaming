package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class TopRateMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val topRateMovies: List<TopRateMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) {
    @Keep
    data class TopRateMovie(
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
        val voteCount: Int,
    ) : DomainMapper<TopRateMovieEntity> {
        override fun toEntity() = TopRateMovieEntity(
            id = id,
            title = title,
            image = posterPath,
            genre = genreIds.firstOrNull(),
            rate = voteAverage
        )
    }
}