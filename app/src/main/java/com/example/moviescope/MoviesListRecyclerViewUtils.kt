package com.example.moviescope

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviescope.databinding.ActivityMainBinding
import com.example.moviescope.databinding.ActivityMainMovieListBinding
import com.example.moviescope.networkUtils.MoviesData

class MoviesViewHolder(
    private val binding: ActivityMainMovieListBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, data: MoviesData, listener: MyItemClickListener) {
        Glide.with(context).load("$imageUrlPrefix${data.poster}").into(binding.ivPoster)
        binding.tvMovieName.text = data.movieTitle

        itemView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.viewGradient.visibility = View.INVISIBLE
                    binding.tvMovieName.visibility = View.INVISIBLE
                    view.scaleY = 1.08f
                    view.scaleX = 1.1f
                    view.elevation = 0.0f
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    resetItemProperties(view)
                }
                MotionEvent.ACTION_UP -> {
                    listener.onItemClicked(data)
                    resetItemProperties(view)
                }
            }
            true
        }
    }

    private fun resetItemProperties(view : View) {
        binding.viewGradient.visibility = View.VISIBLE
        binding.tvMovieName.visibility = View.VISIBLE
        view.scaleX = 1.0f
        view.scaleY = 1.0f
        view.elevation = 18.0f
    }
}


class MovieAdapter(
    private var movieList: ArrayList<MoviesData>,
    private var context: Context,
    private val listener: MyItemClickListener,
): RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ActivityMainMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(context, movieList[position], listener)
    }

    fun refreshList(newDataList: ArrayList<MoviesData>) {
        movieList = newDataList
        notifyItemChanged(movieList.size)
    }
}

interface MyItemClickListener {
    fun onItemClicked(data: MoviesData)
}