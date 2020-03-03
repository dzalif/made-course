package com.kucingselfie.madecourse.repository

import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieDao
import com.kucingselfie.madecourse.entity.MovieEntity

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun insert(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovieBy(id: Int) {
        movieDao.deleteMovieBy(id)
    }

    suspend fun movieById(id: Int) = movieDao.selectMovieBy(id)

    val allMovies: LiveData<List<MovieEntity>> = movieDao.getListMovie()

}