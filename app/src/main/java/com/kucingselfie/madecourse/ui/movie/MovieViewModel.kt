package com.kucingselfie.madecourse.ui.movie

import androidx.lifecycle.ViewModel
import com.kucingselfie.madecourse.model.Movie
import com.kucingselfie.madecourse.util.DataDummy.generateMovies

class MovieViewModel : ViewModel() {
    fun getMovies() : List<Movie> {
        return generateMovies()
    }
}
