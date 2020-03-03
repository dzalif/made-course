package com.kucingselfie.madecourse.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.model.DetailModel
import com.kucingselfie.madecourse.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class DetailMovieViewModel(state: SavedStateHandle, application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository

    companion object {
        const val BUNDLE = "bundle"
    }

    private val savedStateHandle: SavedStateHandle

    private val _movieResult = MutableLiveData<List<MovieEntity>>()
    val movieResult: LiveData<List<MovieEntity>> get() = _movieResult

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> get() = _movieId

    init {
        val movieDao = MovieRoomDatabase.getDatabase(application, viewModelScope).movieDao()
        repository = MovieRepository(movieDao)
        savedStateHandle = state
    }

    private val _detailMovie = MutableLiveData<ResultState<DetailModel>>()
    val detailMovie: LiveData<ResultState<DetailModel>> get() = _detailMovie

    private val _movie = MutableLiveData<DetailModel>()

    fun getDetailMovie(id: Int) {
        setDetailResult(ResultState.Loading())
        viewModelScope.launch {
            withContext(IO) {
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
            withContext(IO) {
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

    fun insertMovie(it: DetailModel?) = viewModelScope.launch {
        it?.let {
            val movieEntity = MovieEntity(
                it.id,
                it.title,
                it.posterPath,
                it.overview
            )
            repository.insert(movieEntity)
        }
    }

    fun removeFromFavorite(id: Int) = viewModelScope.launch {
        repository.deleteMovieBy(id)
    }

    fun setMovieData(data: DetailModel) {
        _movie.value = data
    }

    fun getMovieData() : DetailModel? {
        return _movie.value
    }

    fun getMovieBy(id: Int) {
        viewModelScope.launch {
            val result = repository.movieById(id)
            setMovieResult(result)
        }
    }

    private fun setMovieResult(result: List<MovieEntity>) {
        _movieResult.value = result
    }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

    fun getMovieId() : Int? {
        return _movieId.value
    }
}

