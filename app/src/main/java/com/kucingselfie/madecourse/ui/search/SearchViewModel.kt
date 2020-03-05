package com.kucingselfie.madecourse.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.api.ApiClient
import com.kucingselfie.madecourse.api.response.SearchResponse
import com.kucingselfie.madecourse.common.API_KEY
import com.kucingselfie.madecourse.common.ResultState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel : ViewModel() {
    private val _searchResult = MutableLiveData<ResultState<SearchResponse>>()
    val searchResult: LiveData<ResultState<SearchResponse>> get() = _searchResult

    fun searchMovie(query: String) {
        setSearchResult(ResultState.Loading())
        viewModelScope.launch(IO) {
            try {
                val result = ApiClient.create().getSearchedMovie(API_KEY, query)
                if (result.results.isEmpty()) {
                    setSearchResult(ResultState.NoData())
                    return@launch
                }
                setSearchResult(ResultState.HasData(result))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> setSearchResult(ResultState.NoInternetConnection())
                    else -> setSearchResult(ResultState.Error(R.string.unknown_error))
                }
            }
        }
    }

    private fun setSearchResult(result: ResultState<SearchResponse>) {
        _searchResult.postValue(result)
    }
}
