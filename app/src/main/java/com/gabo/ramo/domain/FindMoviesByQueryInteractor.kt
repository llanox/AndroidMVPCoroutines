package com.gabo.ramo.domain

import android.util.Log
import com.gabo.ramo.data.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.vicpin.krealmextensions.BuildConfig
import com.vicpin.krealmextensions.saveAll
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class FindMoviesByQueryInteractor(cacheDir: File?) : InteractorCommand<Response<List<Movie>>,String>{

    private val retrofit: Retrofit by lazy {

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
        val clientBuilder = OkHttpClient().newBuilder()
        cacheDir?.let {
            clientBuilder.cache(Cache(it, CACHE_SIZE_BYTES))
        }

        val loggingInterceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG){
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        retrofitBuilder.client(clientBuilder.build())

        retrofitBuilder.build()
    }

    override suspend fun execute(query: String): Response<List<Movie>> {
        try {
            val response = retrofit.create(MovieRepository::class.java).listMovieByQuery(query = query)
            val result = response.await()

            result.results?.let {
                it.saveAll()
            }

            return Response.Success(result.results)

        } catch (error: Throwable) {
            val errorMsg = "Error fetching movies for query ${query}"
            Log.e(FindMoviesByQueryInteractor::class.java.simpleName, errorMsg, error)
            return Response.Error(error, errorMsg)
        }
    }

}