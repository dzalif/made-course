package com.kucingselfie.madecourse.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kucingselfie.madecourse.R
import com.kucingselfie.madecourse.databinding.HomeFragmentBinding
import com.kucingselfie.madecourse.ui.favorite.FavoriteFragment
import com.kucingselfie.madecourse.ui.movie.MovieFragment
import com.kucingselfie.madecourse.ui.tvshow.TVShowFragment

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewPager : ViewPager
    private lateinit var viewPagerAdapter : ViewPagerFragment
    private lateinit var tabLayout : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = HomeFragmentBinding.inflate(inflater)
        binding.executePendingBindings()
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        setHasOptionsMenu(true)
        viewPagerAdapter = ViewPagerFragment(childFragmentManager, requireContext())
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager,true)
        return binding.root
    }

    class ViewPagerFragment(fm: FragmentManager, context: Context): FragmentPagerAdapter(fm) {
        private val tabNextMatch = context.resources.getString(R.string.movie)
        private val tabLastMatch = context.resources.getString(R.string.tv_show)

        private val tabTitles = arrayOf(tabNextMatch, tabLastMatch)

        private val pages: List<Fragment> = listOf(
            MovieFragment(),
            TVShowFragment(),
            FavoriteFragment()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
