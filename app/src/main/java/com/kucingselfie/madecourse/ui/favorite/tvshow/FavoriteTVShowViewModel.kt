package com.kucingselfie.madecourse.ui.favorite.tvshow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.TVShowEntity
import com.kucingselfie.madecourse.repository.TVShowRepository

class FavoriteTVShowViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TVShowRepository
    val allTVShows: LiveData<List<TVShowEntity>>

    init {
        val tvShowDao = MovieRoomDatabase.getDatabase(application).tvShowDao()
        repository = TVShowRepository(tvShowDao)
        allTVShows = repository.allTVShows
    }
}
