package com.moviestreaming.data.source.network.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moviestreaming.data.model.CreditsEntity
import com.moviestreaming.data.model.CreditsEntity.CastEntity
import com.moviestreaming.data.model.CreditsEntity.CrewEntity
import com.moviestreaming.utils.mapper.DomainMapper

@Keep
data class CreditsDto(
    @SerializedName("cast")
    val castsDto: List<CastDto>,
    @SerializedName("crew")
    val crewsDto: List<CrewDto>,
    @SerializedName("id")
    val id: Int,
): DomainMapper<CreditsEntity> {
    @Keep
    data class CastDto(
        @SerializedName("adult")
        val adult: Boolean?,
        @SerializedName("cast_id")
        val castId: Int,
        @SerializedName("character")
        val character: String?,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("order")
        val order: Int?,
        @SerializedName("original_name")
        val originalName: String?,
        @SerializedName("popularity")
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?,
    ): DomainMapper<CastEntity> {
        override fun toEntity() = CastEntity(castId, originalName, character, knownForDepartment, profilePath, gender, popularity, creditId)

    }

    @Keep
    data class CrewDto(
        @SerializedName("adult")
        val adult: Boolean?,
        @SerializedName("credit_id")
        val creditId: String,
        @SerializedName("department")
        val department: String?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("job")
        val job: String?,
        @SerializedName("known_for_department")
        val knownForDepartment: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("original_name")
        val originalName: String?,
        @SerializedName("popularity")
        val popularity: Double?,
        @SerializedName("profile_path")
        val profilePath: String?,
    ): DomainMapper<CrewEntity> {
        override fun toEntity() = CrewEntity(creditId, department, gender, job, originalName, profilePath, popularity)

    }

    override fun toEntity() = CreditsEntity(id, castsDto.map { it.toEntity() }, crewsDto.map { it.toEntity() })
}