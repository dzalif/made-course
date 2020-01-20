package com.kucingselfie.madecourse.ui.movie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kucingselfie.madecourse.databinding.DetailMovieFragmentBinding

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
        val model = DetailMovieFragmentArgs.fromBundle(arguments!!).model
        binding.model = model
    }
}
