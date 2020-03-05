package com.kucingselfie.madecourse.ui.search

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.common.ResultState
import com.kucingselfie.madecourse.databinding.SearchFragmentBinding
import com.kucingselfie.madecourse.util.gone
import com.kucingselfie.madecourse.util.visible
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    lateinit var bind: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = SearchFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        initAdapter()
        observeData()
    }

    private fun initAdapter() {
        adapter = SearchAdapter {

        }
        rvSearch.adapter = adapter
    }

    private fun observeData() {
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is ResultState.Loading -> {
                        bind.progressBar.visible()
                    }
                    is ResultState.HasData -> {
                        bind.progressBar.gone()
                        adapter.submitList(it.data.results)
                    }
                    is ResultState.Error -> {
                        bind.progressBar.gone()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.action_search)
        searchView.expandActionView()
        val actionView = searchView.actionView as SearchView
        val etSearch = actionView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        etSearch.setTextColor(resources.getColor(R.color.white))
        etSearch.setHintTextColor(resources.getColor(R.color.white))

        actionView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) {
                    progressBar.gone()
                    tvNoData.visible()
                    rvSearch.gone()
                } else {
                    tvNoData.gone()
                    rvSearch.visible()
                    viewModel.searchMovie(query)
                }
                return false
            }

        })

        MenuItemCompat.setOnActionExpandListener(
            searchView,
            object : MenuItemCompat.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    findNavController().navigateUp()
                    return true
                }

            })

        super.onCreateOptionsMenu(menu, inflater)
    }
}
