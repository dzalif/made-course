package com.kucingselfie.madecourse.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.TvshowFragmentBinding
import com.kucingselfie.madecourse.ui.home.HomeFragmentDirections
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible
import kotlinx.android.synthetic.main.movie_fragment.*

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

        initRecyclerView()
        viewModel.getTVShow()
        observeData()
    }

    private fun observeData() {
        viewModel.tvShows.observe(viewLifecycleOwner, Observer {
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
        adapter = TVShowAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id, false)
            findNavController().navigate(action)
        }
        binding.rvTVshow.adapter = adapter
    }
}
