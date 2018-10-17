package com.gabo.ramo.presentation.moviedetail

import com.gabo.ramo.core.BaseView
import com.gabo.ramo.data.Movie

interface MovieDetailScreen : BaseView{
    fun renderMovieDetail(movie: Movie?)
}