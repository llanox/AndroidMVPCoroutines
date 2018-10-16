package com.gabo.ramo.data

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File
import okhttp3.OkHttpClient


const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "bab0abfc7de8f8d17ad8c0d5dddf8dc4"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 4


interface MovieAPIClient {
    @GET("movie/{category}")
    fun listMovieByCategory(@Path("category") category: String, @Query("api_key") apiKey: String = API_KEY): Deferred<MovieMapper.Result>

}


class MovieRepository(cacheDir: File?) {

    private val retrofit: Retrofit by lazy {

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
        cacheDir?.let {
            retrofitBuilder.client(
                    OkHttpClient()
                            .newBuilder()
                            .cache(Cache(it, CACHE_SIZE_BYTES)).build())
        }

        retrofitBuilder.build()
    }


    suspend fun fetchMovies(category: MovieCategory): Response<List<Movie>> {

        try {
            val response = retrofit.create(MovieAPIClient::class.java).listMovieByCategory(category.code)
            val result = response.await()

            return Response.Success(result.results)

        } catch (error: Throwable) {
            val errorMsg = "Error fetching updated ${category.label} movies"
            Log.e(MovieRepository::class.java.simpleName, errorMsg, error)
            return Response.Error(error, errorMsg)
        }

    }


}

sealed class Response<out T : Any> {

    class Success<out T : Any>(val data: T) : Response<T>()

    class Error(val error: Throwable, val errorMsg: String) : Response<Nothing>()
}

object MovieMapper {
    data class Result(val results: List<Movie>)
}

