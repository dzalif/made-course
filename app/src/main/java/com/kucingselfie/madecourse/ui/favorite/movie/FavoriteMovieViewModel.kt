package com.kucingselfie.madecourse.ui.favorite.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.repository.MovieRepository

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    val allMovies: LiveData<List<MovieEntity>>

    init {
        val movieDao = MovieRoomDatabase.getDatabase(application).movieDao()
        val tvShowDao = MovieRoomDatabase.getDatabase(application).tvShowDao()
        repository = MovieRepository(movieDao, tvShowDao)
        allMovies = repository.allMovies
    }
}
