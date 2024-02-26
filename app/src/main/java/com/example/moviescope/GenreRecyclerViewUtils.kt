package com.example.moviescope

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescope.databinding.ActivityMainGenreListBinding
import com.example.moviescope.networkUtils.GenreData
import com.example.moviescope.networkUtils.MoviesData

class GenreViewHolder(
    private val binding: ActivityMainGenreListBinding,
): RecyclerView.ViewHolder(binding.root) {

    private lateinit var moviesAdapter: MovieAdapter
    fun bind(context: Context, data: GenreData, moviesList: ArrayList<MoviesData>?) {
        binding.tvGenre.text = data.name
        moviesAdapter = MovieAdapter(moviesList!!, context)
        binding.rvHorizontal.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvHorizontal.adapter = moviesAdapter
        moviesAdapter.refreshList(moviesList)
    }
}

class GenreAdapter(
    private var context: Context,
    private var genres: HashMap<Int, GenreData>,
    private var hash: HashMap<Int, ArrayList<MoviesData>>,
    private var genreKeys: MutableList<Int>
): RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ActivityMainGenreListBinding
                .inflate(LayoutInflater
                    .from(parent.context),
                    parent,
                    false)
        )
    }

    override fun getItemCount() = genreKeys.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(context, genres[genreKeys[position]]!!, hash[genreKeys[position]])
    }

    fun refreshList(
        newGenreDataMap: HashMap<Int, GenreData>,
        newHashDataMap: HashMap<Int, ArrayList<MoviesData>>,
        newGenreKeys: MutableList<Int>
    ) {
        genres = newGenreDataMap
        hash = newHashDataMap
        genreKeys = newGenreKeys

        notifyDataSetChanged()
    }
}