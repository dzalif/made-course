package com.kucingselfie.madecourse.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.model.TVShow
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class TvshowViewModel : ViewModel() {
    private val _tvShows = MutableLiveData<ResultState<List<TVShow>>>()
    val tvShows: LiveData<ResultState<List<TVShow>>> get() = _tvShows

    fun getTVShow() {
        viewModelScope.launch {
            withContext(IO) {
                try {
                    val result = ApiClient.create().getTvShows(API_KEY)
                    val tvShows = result.results
                    if (tvShows.isEmpty()) {
                        setTvShowResult(ResultState.NoData())
                        return@withContext
                    }
                    setTvShowResult(ResultState.HasData(tvShows))
                } catch (t: Throwable) {
                    when(t) {
                        is IOException -> setTvShowResult(ResultState.NoInternetConnection())
                        else -> setTvShowResult(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setTvShowResult(result: ResultState<List<TVShow>>) {
        _tvShows.postValue(result)
    }
}
