package com.moviestreaming.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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
    )
}