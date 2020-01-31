package com.kucingselfie.madecourse.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.DetailMovieFragmentBinding
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible

class DetailMovieFragment : Fragment() {
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var binding: DetailMovieFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailMovieFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        binding.lifecycleOwner = this
        arguments?.let {
            val id = DetailMovieFragmentArgs.fromBundle(it).id
            val isMovie = DetailMovieFragmentArgs.fromBundle(it).isMovie

            if (savedInstanceState == null) {
                if (isMovie) viewModel.getDetailMovie(id)
                else viewModel.getDetailTvShow(id)
                observeData()
            } else {
                binding.progressBar.gone()
                val movie = viewModel.getMovieState()
                binding.model = movie
            }
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
                    }
                    is ResultState.Error -> {
                        binding.progressBar.gone()
                    }
                }
            }
        })
    }
}
