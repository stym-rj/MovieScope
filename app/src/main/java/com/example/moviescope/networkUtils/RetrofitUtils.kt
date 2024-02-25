package com.example.moviescope.networkUtils

import com.example.moviescope.GENRE_BASE_URL
import com.example.moviescope.GENRE_ENDPOINT
import com.example.moviescope.MOVIES_BASE_URL
import com.example.moviescope.MOVIES_ENDPOINT
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieDataService {
    @GET(MOVIES_ENDPOINT)
    fun fetchMovies(): Call<List<MoviesData>>
}

interface  GenreDataService {
    @GET(GENRE_ENDPOINT)
    fun fetchGenre(): Call<List<GenreData>>
}

val moviesApi: MovieDataService by lazy {
    Retrofit.Builder()
        .baseUrl(MOVIES_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieDataService::class.java)
}

val genreApi: GenreDataService by lazy {
    Retrofit.Builder()
        .baseUrl(GENRE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenreDataService::class.java)
}