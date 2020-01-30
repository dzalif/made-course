package com.kucingselfie.madecourse.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.model.DetailModel
import kotlinx.coroutines.launch
import java.io.IOException

class DetailMovieViewModel : ViewModel() {

    private val _detailMovie = MutableLiveData<ResultState<DetailModel>>()
    val detailMovie: LiveData<ResultState<DetailModel>> get() = _detailMovie

    fun getDetailMovie(id: Int) {
        setDetailResult(ResultState.Loading())
        viewModelScope.launch {
            try {
                val result = ApiClient.create().getMovieDetail(id, API_KEY)
                setDetailResult(ResultState.HasData(result))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> setDetailResult(ResultState.NoInternetConnection())
                    else -> setDetailResult(ResultState.Error(R.string.unknown_error))
                }
            }

        }

    }

    private fun setDetailResult(result: ResultState<DetailModel>) {
        _detailMovie.postValue(result)
    }

    fun getDetailTvShow(id: Int) {
        setDetailResult(ResultState.Loading())
        viewModelScope.launch {
            try {
                val result = ApiClient.create().getTvDetail(id, API_KEY)
                setDetailResult(ResultState.HasData(result))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> setDetailResult(ResultState.NoInternetConnection())
                    else -> setDetailResult(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }
}

