package com.kucingselfie.madecourse.repository

import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieDao
import com.kucingselfie.madecourse.db.TVShowDao
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.entity.TVShowEntity

class TVShowRepository(private val tvShowDao: TVShowDao) {
    val allTVShows: LiveData<List<TVShowEntity>> = tvShowDao.getListTVShow()
}