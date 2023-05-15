package com.moviestreaming.data.model

data class CreditsEntity(
    val castDto: List<Cast>,
    val crewDto: List<Crew>,
    val id: Int,
) {
    data class Cast(
        val castId: Int,
        val realName: String,
        val character: String,
        val knownForDepartment: String,
        val profileImage: String,
        val gender: Int,
        val popularity: Double,
        val creditId: String
    )

    data class Crew(
        val creditId: String,
        val department: String,
        val gender: Int,
        val job: String,
        val originalName: String,
        val profileImage: String,
        val popularity: Double
    )
}
