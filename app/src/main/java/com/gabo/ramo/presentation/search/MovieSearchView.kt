package com.gabo.ramo.presentation.search

import com.gabo.ramo.core.BaseView
import com.gabo.ramo.data.Movie

interface MovieSearchView: BaseView {
    fun updateListOfMovies(items: List<Movie>)
    fun showErrorFetchingMovies(errorMsg: String)
    fun startSearchQueryAnimation()
    fun stopSearchQueryAnimation()
    fun showModeSearchingResults(size: Int)
}