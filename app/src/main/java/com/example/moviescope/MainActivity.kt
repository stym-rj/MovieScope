package com.example.moviescope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescope.databinding.ActivityMainBinding
import com.example.moviescope.networkUtils.GenreData
import com.example.moviescope.networkUtils.MoviesData
import com.example.moviescope.networkUtils.genreApi
import com.example.moviescope.networkUtils.moviesApi
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var genreList = listOf<GenreData>()
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()

        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pb.visibility = View.VISIBLE
        val hash: HashMap<Int, ArrayList<MoviesData>> = HashMap()
        var moviesData = listOf<MoviesData>()
        genreAdapter = GenreAdapter(genreList, hash)
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
                            val temp = hash[j]
                            temp?.add(i)
                            if (temp != null) {
                                hash[j] = temp
                            }
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

                                val data = response.body() ?: listOf()
                                genreAdapter.refreshList(data)
                            }
                        }

                        override fun onFailure(call: Call<List<GenreData>?>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<MoviesData>?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        genreApi.fetchGenre().enqueue(object : retrofit2.Callback<List<GenreData>?> {
            override fun onResponse(
                call: Call<List<GenreData>?>,
                response: Response<List<GenreData>?>
            ) {
                if (response.isSuccessful) {
                    binding.pb.visibility = View.GONE
                    binding.rvVertical.visibility = View.VISIBLE

                    val data = response.body() ?: listOf()
                        Log.d("printing hash",  hash.toString())
                        Log.d("printing movies",  moviesData.toString())
                    genreAdapter.refreshList(data)
                }
            }

            override fun onFailure(call: Call<List<GenreData>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}