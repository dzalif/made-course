package com.kucingselfie.madecourse.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.databinding.FavoriteMovieFragmentBinding
import com.kucingselfie.madecourse.ui.favorite.FavoriteFragmentDirections
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible

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
                if (it.isEmpty()) {
                    bind.textEmptyData.visible()
                    bind.rvFavoriteMovie.gone()
                } else {
                    bind.textEmptyData.gone()
                    bind.rvFavoriteMovie.visible()
                    adapter.submitList(it)
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = FavoriteMovieAdapter {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailMovieFragment(it.id, true)
            findNavController().navigate(action)
        }
        bind.rvFavoriteMovie.adapter = adapter
    }
}
