package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.utils.Constants
import com.moviestreaming.utils.getImageUrl
import com.moviestreaming.utils.mapper.DomainMapper
import com.moviestreaming.utils.roundedTo2Decimal
import com.moviestreaming.utils.showYear

@Keep
data class MovieDetailDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genresDto: List<GenreDto>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
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
    @SerializedName("production_companies")
    val productionCompaniesDto: List<ProductionCompanyDto>,
    @SerializedName("production_countries")
    val productionCountriesDto: List<ProductionCountryDto>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguagesDto: List<SpokenLanguageDto>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
): DomainMapper<MovieDetailEntity?> {
    @Keep
    data class GenreDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ): DomainMapper<GenreEntity> {
        override fun toEntity() = GenreEntity(id, name)
    }

    @Keep
    data class ProductionCompanyDto(
        @SerializedName("id")
        val id: Int,
        @SerializedName("logo_path")
        val logoPath: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("origin_country")
        val originCountry: String
    )

    @Keep
    data class ProductionCountryDto(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        @SerializedName("name")
        val name: String
    )

    @Keep
    data class SpokenLanguageDto(
        @SerializedName("english_name")
        val englishName: String,
        @SerializedName("iso_639_1")
        val iso6391: String,
        @SerializedName("name")
        val name: String,
    )

    override fun toEntity() = MovieDetailEntity(
        id = id,
        title = title,
        image = getImageUrl(backdropPath),
        Constants.MOVIE,
        rate = roundedTo2Decimal(voteAverage),
        releasedDate = showYear(releaseDate),
        budget = budget,
        description = overview,
        genres = genresDto.map { it.toEntity() }
    )
}