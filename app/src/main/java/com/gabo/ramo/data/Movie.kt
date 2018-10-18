package com.gabo.ramo.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


enum class MovieCategory(val position: Int, val code: String, val label: String) {
    POPULAR(0, "popular", "popular"),
    TOP_RATED(1, "top_rated", "top rated"),
    UPCOMING(2, "upcoming", "upcoming")
}

open class Movie(@PrimaryKey @SerializedName("id") var id: Int = 0,
                 @SerializedName("title") var title: String = "",
                 @SerializedName("poster_path") var posterPath: String? = null,
                 @SerializedName("overview") var overview: String? = null,
                 @SerializedName("video") var hasVideo: Boolean? = null) : RealmObject()

sealed class Response<out T : Any> {

    class Success<out T : Any>(val data: T) : Response<T>()

    class Error(val error: Throwable = Throwable(), val errorMsg: String = "") : Response<Nothing>()
}

object MovieMapper {
    data class Result(val results: List<Movie>)
}