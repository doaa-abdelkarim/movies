package com.example.movies.data.remote.models

import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.TVShow
import com.google.gson.annotations.SerializedName

data class RemoteVideo(

    @field:SerializedName("dates")
    val dates: Dates? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<com.example.movies.data.remote.models.VideosResultsItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

data class Dates(

    @field:SerializedName("maximum")
    val maximum: String? = null,

    @field:SerializedName("minimum")
    val minimum: String? = null
)

data class VideosResultsItem(

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("video")
    val video: Boolean? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("origin_country")
    val originCountry: List<String?>? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

fun VideosResultsItem.asMovieDomainModel(): Movie =
    Movie(
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = title,
        popularity = popularity
    )

fun VideosResultsItem.asTVShowDomainModel(): TVShow =
    TVShow(
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = name,
        popularity = popularity
    )


