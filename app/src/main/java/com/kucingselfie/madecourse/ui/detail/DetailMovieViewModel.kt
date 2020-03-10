package com.kucingselfie.madecourse.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.db.MovieRoomDatabase
import com.kucingselfie.madecourse.entity.MovieEntity
import com.kucingselfie.madecourse.entity.TVShowEntity
import com.kucingselfie.madecourse.model.DetailModel
import com.kucingselfie.madecourse.model.DetailMovieModel
import com.kucingselfie.madecourse.model.DetailTVShowModel
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
    private val _tvResult = MutableLiveData<List<TVShowEntity>>()

    val movieByIdResult: LiveData<List<MovieEntity>> get() = _movieResult
    val tvByIdResult: LiveData<List<TVShowEntity>> get() = _tvResult

    private val _movieId = MutableLiveData<Int>()

    init {
        val movieDao = MovieRoomDatabase.getDatabase(application).movieDao()
        val tvShowDao = MovieRoomDatabase.getDatabase(application).tvShowDao()
        repository = MovieRepository(movieDao, tvShowDao)
        savedStateHandle = state
    }

    private val _detailMovie = MutableLiveData<ResultState<DetailModel>>()
    val detailMovie: LiveData<ResultState<DetailModel>> get() = _detailMovie

    private val _detailMovieData = MutableLiveData<DetailMovieModel>()
    private val _detailTVShowData = MutableLiveData<DetailTVShowModel>()

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

    fun removeFromFavoriteMovie(id: Int) = viewModelScope.launch {
        repository.deleteMovieBy(id)
    }

    fun setMovieData(data: DetailModel) {
        val detail = DetailMovieModel(
            data.id,
            data.title,
            data.posterPath,
            data.overview
        )
        _detailMovieData.value = detail
    }

    fun setTVShowData(data: DetailModel) {
        val detail = DetailTVShowModel(
            data.id,
            data.name,
            data.posterPath,
            data.overview
        )
        _detailTVShowData.value = detail
    }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

    fun getMovieId() : Int? {
        return _movieId.value
    }

    fun removeFromFavoriteTVShow(id: Int) = viewModelScope.launch {
        repository.deleteTVShowById(id)
    }

    fun insertTVShow(it: DetailTVShowModel?) = viewModelScope.launch {
        it?.let {
            val tvShowEntity = TVShowEntity(
                it.id,
                it.name,
                it.posterPath,
                it.overview
            )
            repository.insertTVShow(tvShowEntity)
        }
    }

    fun getDetailMovieData(): DetailMovieModel? {
        return _detailMovieData.value
    }

    fun getDetailTVShowData(): DetailTVShowModel? {
        return _detailTVShowData.value
    }

    fun insertMovie(it: DetailMovieModel?) = viewModelScope.launch {
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

    fun getTVShowBy(id: Int) {
        viewModelScope.launch {
            val result = repository.tvShowById(id)
            setTVResult(result)
        }
    }

    fun getMovieBy(id: Int) {
        viewModelScope.launch {
            val result = repository.movieById(id)
            setMovieResult(result)
        }
    }

    private fun setTVResult(result: List<TVShowEntity>) {
        _tvResult.value = result
    }

    private fun setMovieResult(result: List<MovieEntity>) {
        _movieResult.value = result
    }

}

