package com.gabo.ramo.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

enum class MovieCategory(val position: Int, val code: String, val label: String) {
    POPULAR(0, "popular", "popular"),
    TOP_RATED(1, "top_rated", "top rated"),
    UPCOMING(2, "upcoming", "upcoming")
}

data class Movie(@PrimaryKey @SerializedName("id") val id: String, @SerializedName("title") val title: String, @SerializedName("poster_path") val posterPath: String) : RealmObject()