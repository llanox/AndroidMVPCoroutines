package com.gabo.ramo.domain

import com.gabo.ramo.data.Movie
import com.vicpin.krealmextensions.equalToValue
import com.vicpin.krealmextensions.queryFirst

class FindMovieByIdInteractor : InteractorCommand<Movie?,Int>{
    override suspend fun execute(id: Int): Movie? {
        return Movie().queryFirst{ equalToValue("id", id)}
    }

}