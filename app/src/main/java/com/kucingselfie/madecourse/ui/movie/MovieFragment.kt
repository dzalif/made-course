package com.kucingselfie.madecourse.ui.movie

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.kucingselfie.madecourse.databinding.MovieFragmentBinding
import com.kucingselfie.madecourse.model.DetailModel

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
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        adapter = MovieAdapter {
            val model = DetailModel(
                it.id,
                it.title,
                it.description,
                it.image
            )
            val action = MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(model)
            findNavController().navigate(action)
        }
        binding.rvMovie.adapter = adapter
    }
}
