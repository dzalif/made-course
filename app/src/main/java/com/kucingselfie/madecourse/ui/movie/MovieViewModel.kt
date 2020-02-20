package com.kucingselfie.madecourse.ui.movie

import androidx.lifecycle.*
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.model.Movie
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieViewModel(state: SavedStateHandle) : ViewModel() {

    companion object {
        const val MOVIE = "movie"
    }

    private val savedStateHandle = state

    private val _movies = MutableLiveData<ResultState<List<Movie>>>()
    val movies: LiveData<ResultState<List<Movie>>> get() = _movies

    fun getMovies() {
        setMovieResult(ResultState.Loading())
        viewModelScope.launch {
            withContext(IO) {
                try {
                    val result = ApiClient.create().getMovies(API_KEY)
                    val movies = result.results
                    if (movies.isEmpty()) {
                        setMovieResult(ResultState.NoData())
                        return@withContext
                    }
                    saveMovieState(movies)
                    setMovieResult(ResultState.HasData(movies))
                } catch (t: Throwable) {
                    when(t) {
                        is IOException -> setMovieResult(ResultState.NoInternetConnection())
                        else -> setMovieResult(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private fun setMovieResult(result: ResultState<List<Movie>>) {
        _movies.postValue(result)
    }

    private fun saveMovieState(result: List<Movie>) {
        savedStateHandle.set(MOVIE, result)
    }

    fun getMovieState() : List<Movie>? {
        return savedStateHandle.get(MOVIE)
    }
}
