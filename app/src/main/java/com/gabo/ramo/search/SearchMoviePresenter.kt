package com.gabo.ramo.search

import android.content.Context
import com.gabo.ramo.core.BasePresenter
import com.gabo.ramo.core.CoroutineContextProvider
import com.gabo.ramo.data.MovieCategory
import com.gabo.ramo.data.MovieRepository
import com.gabo.ramo.data.Response
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class SearchMoviePresenter(context: Context?) : BasePresenter<SearchMovieView>() {

    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()
    private val movieRepository: MovieRepository = MovieRepository(context?.cacheDir)

    fun findMoviesBy(query: String?) {
        view?.apply {

            // Execute query to API


        }
    }

    fun findAllMoviesByCategory(category: MovieCategory) {

        GlobalScope.launch(contextPool.Main) {

            val listOfMovies = withContext(contextPool.IO) {
                movieRepository.fetchMovies(category)
            }

            when (listOfMovies) {
                is Response.Success -> view?.updateListOfMovies(listOfMovies.data)
                is Response.Error -> view?.showErrorFetchingMovies(listOfMovies.errorMsg)
            }

        }

    }

}


