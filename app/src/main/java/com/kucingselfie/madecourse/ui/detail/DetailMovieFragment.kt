package com.kucingselfie.madecourse.ui.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.DetailMovieFragmentBinding
import com.kucingselfie.madecourse.model.DetailModel
import com.kucingselfie.madecourse.util.CustomViewModelFactory
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible

class DetailMovieFragment : Fragment() {
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var bind: DetailMovieFragmentBinding
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var isMovie: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DetailMovieFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, CustomViewModelFactory(this, requireActivity().application)).get(DetailMovieViewModel::class.java)
        bind.lifecycleOwner = this
        setHasOptionsMenu(true)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val id = DetailMovieFragmentArgs.fromBundle(it).id
            isMovie = DetailMovieFragmentArgs.fromBundle(it).isMovie

            viewModel.setMovieId(id)

            viewModel.getMovieBy(id)
            viewModel.getTVShowBy(id)

            if (savedInstanceState == null) {
                if (isMovie) viewModel.getDetailMovie(id)
                else viewModel.getDetailTvShow(id)
            } else {
                bind.progressBar.gone()
                val movie = viewModel.getMovieState()
                bind.model = movie
            }
            observeData()
        }
    }

    private fun observeData() {
        viewModel.detailMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is ResultState.Loading -> {
                        bind.progressBar.visible()
                    }
                    is ResultState.HasData -> {
                        bind.progressBar.gone()
                        bind.model = it.data
                        setData(isMovie, it.data)
                    }
                    is ResultState.Error -> {
                        bind.progressBar.gone()
                    }
                }
            }
        })

        viewModel.movieByIdResult.observe(viewLifecycleOwner, Observer {
            val id = viewModel.getMovieId()
            it?.let {
                if (it.isEmpty()) {
                    return@Observer
                } else {
                    setToFavorite(it[0].id, id)
                }
                updateFavorite()
            }
        })

        viewModel.tvByIdResult.observe(viewLifecycleOwner, Observer {
            val id = viewModel.getMovieId()
            it?.let {
                if (it.isEmpty()) {
                    return@Observer
                } else {
                    setToFavorite(it[0].id, id)
                }
                updateFavorite()
            }
        })
    }

    private fun setToFavorite(favoriteId: Int, currentId: Int?) {
        if (favoriteId == currentId) {
            isFavorite = true
        }
    }

    private fun setData(isMovie: Boolean, it: DetailModel) {
        if (isMovie) {
            viewModel.setMovieData(it)
        } else {
            viewModel.setTVShowData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        updateFavorite()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) {
                    if (isMovie) removeFromFavoriteMovie() else removeFromFavoriteTVShow()
                } else {
                    if (isMovie) addToFavoriteMovie() else addToFavoriteTVShow()
                }
                isFavorite = !isFavorite
                updateFavorite()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToFavoriteTVShow() {
        val model = viewModel.getDetailTVShowData()
        model?.let {
            viewModel.insertTVShow(it)
        }
    }

    private fun addToFavoriteMovie() {
        val model = viewModel.getDetailMovieData()
        model?.let {
            viewModel.insertMovie(it)
        }
    }

    private fun removeFromFavoriteTVShow() {
        val model = viewModel.getDetailTVShowData()
        model?.let {
            viewModel.removeFromFavoriteTVShow(it.id)
        }
    }

    private fun removeFromFavoriteMovie() {
        val model = viewModel.getDetailMovieData()
        model?.let {
            viewModel.removeFromFavoriteMovie(it.id)
        }
    }

    private fun updateFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_white_fill)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_white)
    }
}
