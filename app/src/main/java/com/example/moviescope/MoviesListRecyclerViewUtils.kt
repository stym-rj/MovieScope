package com.example.moviescope

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviescope.databinding.ActivityMainGenreListBinding
import com.example.moviescope.databinding.ActivityMainMovieListBinding
import com.example.moviescope.networkUtils.GenreData
import com.example.moviescope.networkUtils.MoviesData

class MoviesViewHolder(
    private val binding: ActivityMainMovieListBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, data: MoviesData) {
        Glide.with(context).load("$imageUrlPrefix${data.poster}").into(binding.ivPoster)
        binding.tvMovieName.text = data.movieTitle
    }
}


class MovieAdapter(
    private var movieList: ArrayList<MoviesData>,
    private var context: Context
): RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ActivityMainMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(context, movieList[position])
    }

    fun refreshList(newDataList: ArrayList<MoviesData>) {
        movieList = newDataList
        notifyDataSetChanged()
    }
}