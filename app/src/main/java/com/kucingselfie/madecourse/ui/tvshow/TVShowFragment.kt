package com.kucingselfie.madecourse.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.databinding.TvshowFragmentBinding
import com.kucingselfie.madecourse.model.DetailModel
import com.kucingselfie.madecourse.ui.home.HomeFragmentDirections

class TVShowFragment : Fragment() {
    private lateinit var viewModel: TvshowViewModel
    private lateinit var binding: TvshowFragmentBinding
    private lateinit var adapter: TVShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TvshowFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(TvshowViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        adapter = com.kucingselfie.madecourse.ui.tvshow.TVShowAdapter {
            val model = DetailModel(
                it.id,
                it.title,
                it.description,
                it.image
            )
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(model)
            findNavController().navigate(action)
        }
        binding.rvMovie.adapter = adapter
    }

}
