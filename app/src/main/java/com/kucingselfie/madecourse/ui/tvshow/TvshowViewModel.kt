package com.kucingselfie.madecourse.ui.tvshow

import androidx.lifecycle.ViewModel
import com.kucingselfie.madecourse.model.TVShow
import com.kucingselfie.madecourse.util.DataDummy.generateTvShows

class TvshowViewModel : ViewModel() {
    fun getTVShow() : List<TVShow> {
        return generateTvShows()
    }
}
