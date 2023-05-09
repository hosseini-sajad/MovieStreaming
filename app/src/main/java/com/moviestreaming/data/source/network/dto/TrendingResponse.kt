package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class TrendingResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val trending: List<Trending>?,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) {
    @Keep
    data class Trending(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String,
        @SerializedName("first_air_date")
        val firstAirDate: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("name")
        val name: String?,
        @SerializedName("origin_country")
        val originCountry: List<String>,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_name")
        val originalName: String,
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
    ): DomainMapper<TrendingEntity> {
        override fun toEntity(): TrendingEntity = TrendingEntity(
            id = id, title = name ?: originalTitle
            , image = backdropPath, mediaType = mediaType
        )
    }
}