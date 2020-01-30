package com.kucingselfie.madecourse.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.model.Movie
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel : ViewModel() {
    private val _movies = MutableLiveData<ResultState<List<Movie>>>()
    val movies: LiveData<ResultState<List<Movie>>> get() = _movies

    fun getMovies() {
        setMovieResult(ResultState.Loading())
        viewModelScope.launch {
            try {
                val result = ApiClient.create().getMovies(API_KEY)
                val movies = result.results
                if (movies.isEmpty()) {
                    setMovieResult(ResultState.NoData())
                    return@launch
                }
                setMovieResult(ResultState.HasData(movies))
            } catch (t: Throwable) {
                when(t) {
                    is IOException -> setMovieResult(ResultState.NoInternetConnection())
                    else -> setMovieResult(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

    private fun setMovieResult(result: ResultState<List<Movie>>) {
        _movies.postValue(result)
    }
}
