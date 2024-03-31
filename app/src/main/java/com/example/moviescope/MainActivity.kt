package com.example.moviescope

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviescope.databinding.ActivityMainBinding
import com.example.moviescope.databinding.ActivityMainMovieListBinding
import com.example.moviescope.networkUtils.GenreData
import com.example.moviescope.networkUtils.MoviesData
import com.example.moviescope.networkUtils.genreApi
import com.example.moviescope.networkUtils.moviesApi
import retrofit2.Call
import retrofit2.Response
import java.lang.Math.abs
import java.util.Arrays

class MainActivity : AppCompatActivity(), MyItemClickListener {

    private lateinit var binding:ActivityMainBinding
    private var genreHash = HashMap<Int, GenreData>()
    private var genreKeys = mutableListOf<Int>()
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var slideShowAdapter: SlideShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()

        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        Slide Show
        slideShowInit()
        setUpTransformer()
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3500)
            }
        })


        binding.pb.visibility = View.VISIBLE
        val hash: HashMap<Int, ArrayList<MoviesData>> = HashMap()
        var moviesData: List<MoviesData>
        genreAdapter = GenreAdapter(this, genreHash, hash, genreKeys, this)
        binding.rvVertical.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL, false)
        binding.rvVertical.adapter = genreAdapter

        moviesApi.fetchMovies().enqueue(object: retrofit2.Callback<List<MoviesData>?> {
            override fun onResponse(
                call: Call<List<MoviesData>?>,
                response: Response<List<MoviesData>?>
            ) {
                if (response.isSuccessful) {
                    moviesData = response.body() ?: listOf()
                    for (i in moviesData) {
                        for (j in i.genreList) {
                            if (hash[j] == null) {
                                hash[j] = ArrayList()
                            }
                            hash[j]?.add(i)
//                            val temp = hash[j]
//                            temp?.add(i)
//                            if (temp != null) {
//                                hash[j] = temp
//                            }
                        }
                    }

                    genreApi.fetchGenre().enqueue(object : retrofit2.Callback<List<GenreData>?> {
                        override fun onResponse(
                            call: Call<List<GenreData>?>,
                            response: Response<List<GenreData>?>
                        ) {
                            if (response.isSuccessful) {
                                binding.pb.visibility = View.GONE
                                binding.rvVertical.visibility = View.VISIBLE
                                Log.d("movie", response.body().toString())

                                val data = response.body() ?: listOf()
                                for (i in data) {
                                    genreHash[i.id] = i
                                }
                                genreAdapter.refreshList(genreHash, hash, hash.keys.toMutableList())

                                Log.d("printing hash",  hash.toString())
                                Log.d("printing movies",  moviesData.toString())
                            }
                        }

                        override fun onFailure(call: Call<List<GenreData>?>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Genre : ${t.message}", Toast.LENGTH_LONG).show()
                            Log.d("printing failure in genre : ", "${t.message}")
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<MoviesData>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Movies : ${t.message}", Toast.LENGTH_LONG).show()
                Log.d("printing failure in movies : ", "${t.message}")
            }

        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3500)
    }

    private val runnable = Runnable {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun slideShowInit() {
        viewPager = binding.viewPager
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList(
            listOf(
                R.drawable.avatar,
                R.drawable.avengers_endgame,
                R.drawable.avengers_infinitywar,
                R.drawable.thor,
                R.drawable.justice_league,
                R.drawable.once_upon_a_time,
                R.drawable.onward,
                R.drawable.mortal_engines )
        )

        slideShowAdapter = SlideShowAdapter(imageList, viewPager)
        binding.viewPager.adapter = slideShowAdapter
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer {page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager.setPageTransformer(transformer)
    }

    override fun onItemClicked(data: MoviesData) {
        Intent(this, MovieDescription::class.java).also {
            it.putExtra("moviesData", data)

//            it.putExtra("backgroundImage", data.backgroundImage)
//            it.putExtra("posterImage", data.poster)
//            it.putExtra("movieTitle", data.movieTitle)
//            it.putExtra("rating", data.rating)
//            it.putExtra("ratingCount", data.ratingCount)
//            it.putExtra("about", data.about)
//            it.putExtra("language", data.language)
//            it.putIntegerArrayListExtra("genre", ArrayList(data.genreList))
//            it.putExtra("releaseDate", data.releaseDate)
            startActivity(it)
        }
    }
}