package com.example.moviescope.networkUtils

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String
)