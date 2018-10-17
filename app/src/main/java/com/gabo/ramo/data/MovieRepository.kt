package com.gabo.ramo.data

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "bab0abfc7de8f8d17ad8c0d5dddf8dc4"
const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 4
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"



interface MovieRepository {
    @GET("movie/{category}")
    fun listMovieByCategory(@Path("category") category: String, @Query("api_key") apiKey: String = API_KEY): Deferred<MovieMapper.Result>

    @GET("search/movie/")
    fun listMovieByQuery(@Query("api_key") apiKey: String = API_KEY, @Query("query") query: String): Deferred<MovieMapper.Result>

}