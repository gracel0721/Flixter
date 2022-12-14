package com.example.flixter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL =
    "https://api.themoviedb.org/3/movie/now_playing?api_key=12901f72c4cbac15c5e22dc40ae7b77d&language=en-US&page=1"
class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById<RecyclerView>(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON Data $json")
                try{
                    val movieArr = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJSONarr(movieArr))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list: $movies")
                }catch(e: JSONException){
                    Log.e(TAG, "Encountered exception $e")
                }

            }

        })
    }
}