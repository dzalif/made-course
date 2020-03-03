package com.kucingselfie.madecourse.ui.favorite


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.databinding.FragmentFavoriteBinding
import com.kucingselfie.madecourse.ui.favorite.movie.FavoriteMovieFragment
import com.kucingselfie.madecourse.ui.favorite.tvshow.FavoriteTVShowFragment

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewPager : ViewPager
    private lateinit var viewPagerAdapter : ViewPagerFragment
    private lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater)
        binding.executePendingBindings()
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        viewPagerAdapter =
            ViewPagerFragment(
                childFragmentManager,
                requireContext()
            )
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager,true)

        return binding.root
    }

    class ViewPagerFragment(fm: FragmentManager, context: Context): FragmentPagerAdapter(fm) {
        private val tabMovie = context.resources.getString(R.string.movie)
        private val tabTVShow = context.resources.getString(R.string.tv_show)

        private val tabTitles = arrayOf(tabMovie, tabTVShow)

        private val pages = listOf(
            FavoriteMovieFragment(),
            FavoriteTVShowFragment()
        )
        override fun getItem(position: Int): Fragment {
            return pages[position]
        }

        override fun getCount(): Int {

            return pages.size
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }
}
