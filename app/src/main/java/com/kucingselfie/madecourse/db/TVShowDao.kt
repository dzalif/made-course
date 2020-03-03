package com.kucingselfie.madecourse.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kucingselfie.madecourse.entity.TVShowEntity

@Dao
interface TVShowDao {
    @Query("SELECT * from tvshow")
    fun getListTVShow(): LiveData<List<TVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(tvshow: TVShowEntity)

    @Query("DELETE FROM tvshow WHERE id = :tvShowId")
    suspend fun deleteTVShowBy(tvShowId: Int)

    @Query("SELECT * FROM tvshow WHERE id = :tvShowId")
    suspend fun selectTVShowBy(tvShowId: Int) : List<TVShowEntity>
}