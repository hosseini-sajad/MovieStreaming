package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.CreditsEntity.Cast
import com.moviestreaming.data.model.CreditsEntity.Crew
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class CreditsDto(
    @SerializedName("cast")
    val castDto: List<CastDto>,
    @SerializedName("crew")
    val crewDto: List<CrewDto>,
    @SerializedName("id")
    val id: Int,
) {
    @Keep
    data class CastDto(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("cast_id")
        val castId: Int,
        @SerializedName("character")
        val character: String,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("order")
        val order: Int,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String,
    ): DomainMapper<Cast> {
        override fun toEntity() = Cast(id, originalName, character, knownForDepartment, profilePath, gender, popularity, creditId)

    }

    @Keep
    data class CrewDto(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("department")
        val department: String,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("job")
        val job: String,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("popularity")
        val popularity: Double,
        @SerializedName("profile_path")
        val profilePath: String,
    ): DomainMapper<Crew> {
        override fun toEntity() = Crew(creditId, department, gender, job, originalName, profilePath, popularity)

    }
}