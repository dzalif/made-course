package com.kucingselfie.madecourse.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kucingselfie.madecourse.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * from movie")
    fun getListMovie(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(word: MovieEntity)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovie()

    @Query("DELETE FROM movie WHERE id = :movieId")
    suspend fun deleteMovieBy(movieId: Int)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun selectMovieBy(movieId: Int) : List<MovieEntity>
}