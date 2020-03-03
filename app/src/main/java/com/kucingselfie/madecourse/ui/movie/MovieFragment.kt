package com.kucingselfie.madecourse.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.MovieFragmentBinding
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: MovieFragmentBinding
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        initRecyclerView()

        if (savedInstanceState == null) {
            viewModel.getMovies()
        } else {
            binding.progressBar.gone()
            val result = viewModel.getMovieState()
            adapter.submitList(result)
        }
        observeData()
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is ResultState.Loading -> {
                        progressBar.visible()
                    }
                    is ResultState.NoData -> {
                        progressBar.gone()
                    }
                    is ResultState.HasData -> {
                        progressBar.gone()
                        adapter.submitList(it.data)
                    }
                    is ResultState.Error -> {
                        progressBar.gone()
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter {
            val action = MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(it.id, true)
            findNavController().navigate(action)
        }
        binding.rvMovie.adapter = adapter
    }
}
