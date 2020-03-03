package com.kucingselfie.madecourse.ui.favorite.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.repository.MovieRepository

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    val allMovies: LiveData<List<MovieEntity>>

    init {
        val movieDao = MovieRoomDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies
    }
}
