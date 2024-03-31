package com.example.moviescope.networkUtils

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoviesData(
    @SerializedName("original_title")
    val movieTitle : String,

    // about the movie
    @SerializedName("overview")
    val about : String,

    @SerializedName("poster_path")
    val poster : String,

    // background image of movie
    @SerializedName("backdrop_path")
    val backgroundImage : String,

    // genre ids which corresponds to genre data in genre api
    @SerializedName("genre_ids")
    val genreList : List<Int>,

    @SerializedName("original_language")
    val language : String,

    @SerializedName("release_date")
    val releaseDate : String,

    @SerializedName("vote_average")
    val rating : Float,

    @SerializedName("vote_count")
    val ratingCount : Int,
) : Serializable