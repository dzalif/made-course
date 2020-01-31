package com.kucingselfie.madecourse.ui.detail

import androidx.lifecycle.*
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.model.DetailModel
import kotlinx.coroutines.launch
import java.io.IOException

class DetailMovieViewModel(state: SavedStateHandle) : ViewModel() {

    companion object {
        const val BUNDLE = "bundle"
    }

    private val savedStateHandle = state

    private val _detailMovie = MutableLiveData<ResultState<DetailModel>>()
    val detailMovie: LiveData<ResultState<DetailModel>> get() = _detailMovie

    fun getDetailMovie(id: Int) {
        setDetailResult(ResultState.Loading())
        viewModelScope.launch {
            try {
                val result = ApiClient.create().getMovieDetail(id, API_KEY)
                saveMovieState(result)
                setDetailResult(ResultState.HasData(result))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> setDetailResult(ResultState.NoInternetConnection())
                    else -> setDetailResult(ResultState.Error(R.string.unknown_error))
                }
            }

        }

    }

    private fun saveMovieState(result: DetailModel) {
        savedStateHandle.set(BUNDLE, result)
    }

    fun getMovieState() : DetailModel? {
        return savedStateHandle.get(BUNDLE)
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

