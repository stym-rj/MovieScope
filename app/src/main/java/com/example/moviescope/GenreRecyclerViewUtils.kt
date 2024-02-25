package com.example.moviescope

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescope.databinding.ActivityMainGenreListBinding
import com.example.moviescope.networkUtils.GenreData
import com.example.moviescope.networkUtils.MoviesData

class GenreViewHolder(
    private val binding: ActivityMainGenreListBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GenreData, list: ArrayList<MoviesData>?) {
        binding.tvGenre.text = data.name

    }
}

class GenreAdapter(
    private var genres: List<GenreData>,
    private var hash: HashMap<Int, ArrayList<MoviesData>>
): RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(ActivityMainGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position], hash[genres[position].id])
    }

    fun refreshList(newDataList: List<GenreData>) {
        genres = newDataList
        notifyDataSetChanged()
    }
}