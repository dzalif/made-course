package com.kucingselfie.madecourse.ui.favorite.tvshow

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kucingselfie.madecourse.R

class FavoriteTVShowFragment : Fragment() {

    companion object {
        fun newInstance() =
            FavoriteTVShowFragment()
    }

    private lateinit var viewModel: FavoriteTvshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tvshow_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteTvshowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
