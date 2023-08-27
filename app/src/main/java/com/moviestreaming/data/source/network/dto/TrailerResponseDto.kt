package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.TrailerEntity
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class TrailerResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val trailers: List<Trailer>
) {
    @Keep
    data class Trailer(
        @SerializedName("id")
        val id: String,
        @SerializedName("iso_3166_1")
        val iso31661: String,
        @SerializedName("iso_639_1")
        val iso6391: String,
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("official")
        val official: Boolean,
        @SerializedName("published_at")
        val publishedAt: String,
        @SerializedName("site")
        val site: String,
        @SerializedName("size")
        val size: Int,
        @SerializedName("type")
        val type: String
    ): DomainMapper<TrailerEntity> {
        override fun toEntity() = TrailerEntity(
            official,
            key,
            site,
            name
        )

    }
}