package com.kucingselfie.madecourse.util

import android.app.Application
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.kucingselfie.madecourse.ui.detail.DetailMovieViewModel

class CustomViewModelFactory(
    state: SavedStateRegistryOwner,
    private val application: Application) : AbstractSavedStateViewModelFactory(state, null) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, state: SavedStateHandle) =
        DetailMovieViewModel(state, application) as T
}