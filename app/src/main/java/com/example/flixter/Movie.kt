package com.example.flixter

import org.json.JSONArray

data class Movie (
    val movieId: Int,
    val title: String,
    val overview: String,
    private val posterPath: String
){
    val posterImageUrl ="https://image.tmdb.org/t/p/w342/$posterPath"
    companion object{
        fun fromJSONarr(movieArr: JSONArray) :List<Movie> {
            val movies = mutableListOf<Movie>()
            for(i in 0 until movieArr.length()){
                val movieJson = movieArr.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")

                    )
                )
            }
            return movies
        }

    }
}
