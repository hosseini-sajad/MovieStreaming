package com.moviestreaming.data.model

data class CreditsEntity(
    val id: Int,
    val casts: List<CastEntity>,
    val crews: List<CrewEntity>,
) {
    data class CastEntity(
        val castId: Int,
        val realName: String?,
        val character: String?,
        val knownForDepartment: String?,
        val profileImage: String?,
        val gender: Int?,
        val popularity: Double?,
        val creditId: String?
    )

    data class CrewEntity(
        val creditId: String,
        val department: String?,
        val gender: Int?,
        val job: String?,
        val originalName: String?,
        val profileImage: String?,
        val popularity: Double?
    )
}
