package com.example.moviescope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moviescope.databinding.ActivityMainMovieListBinding
import com.example.moviescope.databinding.ActivityMovieDescriptionBinding

class MovieDescription : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle?= intent.extras
        val backgroundImage = bundle!!.getString("backgroundImage")
        val posterImage = bundle.getString("posterImage")
        val movieTitle = bundle.getString("movieTitle")
        val rating = bundle.getFloat("rating")
        val ratingCount = bundle.getInt("ratingCount")
        val about = bundle.getString("about")
        val language = when (bundle.getString("language")) {
            "en" -> "English"
            "ko" -> "Korean"
            "de" -> "German"
            "es" -> "Spanish"
            "ja" -> "Japanese"
            "id" -> "Indonesian"
            "sv" -> "Swedish"
            else -> "Unknown"
        }
        val genre = bundle.getIntegerArrayList("genre")
        val releaseDate = bundle.getString("releaseDate")

        Glide.with(this).load("${imageUrlPrefix}${backgroundImage}").into(binding.ivMovieBackground)
        binding.tvMovieName.text = movieTitle
        binding.tvRatingCount.text = "(${ratingCount.toString()})"
        binding.rbRating.rating = rating / 2f
        binding.tvAbout.text = about
        binding.tvLanguage.text = language
        binding.tvGenre.text = genre.toString()
        binding.tvReleaseDate.text = releaseDate
        Glide.with(this).load("${imageUrlPrefix}${posterImage}").into(binding.iv1)
        Glide.with(this).load("${imageUrlPrefix}${backgroundImage}").into(binding.iv2)
    }
}