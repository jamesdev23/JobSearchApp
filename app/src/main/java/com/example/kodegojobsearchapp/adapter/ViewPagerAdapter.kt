package com.example.kodegojobsearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.databinding.ItemTabBinding

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, var context: Context) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    private val tabTitles = arrayOf("Home", "Search", "Profile", "Account")

    private val tabIcons = arrayOf(
        R.drawable.baseline_home_24,
        R.drawable.baseline_format_list_bulleted_24,
        R.drawable.baseline_supervised_user_circle_24,
        R.drawable.baseline_manage_accounts_24
    )

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun getTabView(position: Int, parent: ViewGroup): View {
        val itemBinding = ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val tabTitle = itemBinding.tabTitle
        val tabIcon = itemBinding.tabIcon
        tabTitle.text = tabTitles[position]
        tabIcon.setImageResource(tabIcons[position])
        return itemBinding.root
    }
}
