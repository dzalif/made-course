package com.kucingselfie.madecourse.repository

import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieDao
import com.kucingselfie.madecourse.entity.MovieEntity

class MovieRepository(private val movieDao: MovieDao) {
    val listMovie: LiveData<List<MovieEntity>> = movieDao.getListMovie()

    suspend fun insert(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }
}