package com.example.kodegojobsearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kodegojobsearchapp.R
import com.example.kodegojobsearchapp.applicant_viewpager.AccountFragment
import com.example.kodegojobsearchapp.applicant_viewpager.HomeFragment
import com.example.kodegojobsearchapp.applicant_viewpager.JobListingFragment
import com.example.kodegojobsearchapp.applicant_viewpager.ProfileFragment
import java.security.AccessController.getContext

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var context: Context) : FragmentStateAdapter(fragmentActivity) {

    private val tabTitles = arrayOf("Home", "Search", "Profile", "Account")
    private val tabIcons = arrayOf(R.drawable.baseline_home_24, R.drawable.baseline_format_list_bulleted_24, R.drawable.baseline_supervised_user_circle_24, R.drawable.baseline_manage_accounts_24)

    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> JobListingFragment()
            2 -> ProfileFragment()
            3 -> AccountFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_item, null)
        val tabTitle = view.findViewById<TextView>(R.id.tab_title)
        val tabIcon = view.findViewById<ImageView>(R.id.tab_icon)
        tabTitle.text = tabTitles[position]
        tabIcon.setImageResource(tabIcons[position])
        return view
    }
}
