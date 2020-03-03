package com.kucingselfie.madecourse.repository

import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieDao
import com.kucingselfie.madecourse.db.TVShowDao
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.entity.TVShowEntity

class MovieRepository(private val movieDao: MovieDao, private val tvShowDao: TVShowDao) {
    suspend fun insert(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovieBy(id: Int) {
        movieDao.deleteMovieBy(id)
    }

    suspend fun movieById(id: Int) = movieDao.selectMovieBy(id)

    suspend fun deleteTVShowById(id: Int) {
        tvShowDao.deleteTVShowBy(id)
    }

    suspend fun insertTVShow(data: TVShowEntity) {
        tvShowDao.insertTVShow(data)
    }

    suspend fun tvShowById(id: Int): List<TVShowEntity> = tvShowDao.selectTVShowBy(id)

    val allMovies: LiveData<List<MovieEntity>> = movieDao.getListMovie()

}