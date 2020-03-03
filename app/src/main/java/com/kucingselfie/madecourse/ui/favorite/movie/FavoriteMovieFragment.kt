package com.kucingselfie.madecourse.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kucingselfie.madecourse.databinding.FavoriteMovieFragmentBinding

class FavoriteMovieFragment : Fragment() {

    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var bind: FavoriteMovieFragmentBinding
    private lateinit var adapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FavoriteMovieFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteMovieViewModel::class.java)
        initAdapter()

        viewModel.allMovies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initAdapter() {
        adapter = FavoriteMovieAdapter {

        }
        bind.rvFavoriteMovie.adapter = adapter
    }
}
