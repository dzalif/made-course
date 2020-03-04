package com.kucingselfie.madecourse.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.TvshowFragmentBinding
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(TvshowViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        initRecyclerView()

        if (savedInstanceState == null) {
            viewModel.getTVShow()
        } else {
            binding.progressBar.gone()
            val result = viewModel.getTVState()
            adapter.submitList(result)
        }
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
            val action = TVShowFragmentDirections.actionTVShowFragmentToDetailMovieFragment(it.id, false)
            findNavController().navigate(action)
        }
        binding.rvTVshow.adapter = adapter
    }
}
