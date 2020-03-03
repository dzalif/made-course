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
import com.kucingselfie.madecourse.util.CustomViewModelFactory
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible

class DetailMovieFragment : Fragment() {
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var binding: DetailMovieFragmentBinding
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailMovieFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, CustomViewModelFactory(this, requireActivity().application)).get(DetailMovieViewModel::class.java)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val id = DetailMovieFragmentArgs.fromBundle(it).id
            val isMovie = DetailMovieFragmentArgs.fromBundle(it).isMovie

            viewModel.getMovieBy(id)
            viewModel.setMovieId(id)

            if (savedInstanceState == null) {
                if (isMovie) viewModel.getDetailMovie(id)
                else viewModel.getDetailTvShow(id)
            } else {
                binding.progressBar.gone()
                val movie = viewModel.getMovieState()
                binding.model = movie
            }
            observeData()
        }
    }

    private fun observeData() {
        viewModel.detailMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is ResultState.Loading -> {
                        binding.progressBar.visible()
                    }
                    is ResultState.HasData -> {
                        binding.progressBar.gone()
                        binding.model = it.data
                        viewModel.setMovieData(it.data)
                    }
                    is ResultState.Error -> {
                        binding.progressBar.gone()
                    }
                }
            }
        })

        viewModel.movieResult.observe(viewLifecycleOwner, Observer {
            val id = viewModel.getMovieId()
            it?.let {
                if (it.isEmpty()) {
                    return@Observer
                } else {
                    if (it[0].id == id) {
                        isFavorite = true
                    }
                }
                setFavorite()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                } else {
                    addedToFavorite()
                }
                isFavorite = !isFavorite
                setFavorite()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removeFromFavorite() {
        val model = viewModel.getMovieData()
        model?.let {
            viewModel.removeFromFavorite(it.id)
        }
    }

    private fun addedToFavorite() {
        val model = viewModel.getMovieData()
        model?.let {
            viewModel.insertMovie(it)
        }
    }

    private fun setFavorite() {
        changeStateMenuIcon()
    }

    private fun changeStateMenuIcon() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_white_fill)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_white)
    }
}
