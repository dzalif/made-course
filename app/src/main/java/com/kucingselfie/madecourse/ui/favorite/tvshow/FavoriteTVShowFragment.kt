package com.kucingselfie.madecourse.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kucingselfie.madecourse.databinding.FavoriteTvshowFragmentBinding

class FavoriteTVShowFragment : Fragment() {

    private lateinit var viewModel: FavoriteTVShowViewModel
    private lateinit var bind: FavoriteTvshowFragmentBinding
    private lateinit var adapter: FavoriteTVShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FavoriteTvshowFragmentBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteTVShowViewModel::class.java)

        initAdapter()

        viewModel.allTVShows.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initAdapter() {
        adapter = FavoriteTVShowAdapter {

        }
        bind.rvFavoriteTvShow.adapter = adapter
    }

}
