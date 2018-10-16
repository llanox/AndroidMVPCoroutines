package com.gabo.ramo.search

import com.gabo.ramo.core.BaseView
import com.gabo.ramo.data.Movie

interface SearchMovieView: BaseView {
    fun updateListOfMovies(items: List<Movie>)
    fun showErrorFetchingMovies(errorMsg: String)
}