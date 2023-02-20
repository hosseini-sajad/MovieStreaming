package com.moviestreaming.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.utils.DomainMapper

@Keep
data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
) {
    @Keep
    data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    ): DomainMapper<GenreEntity> {
        override fun toEntity(): GenreEntity {
            return GenreEntity(id = id , name = name)
        }
    }
}